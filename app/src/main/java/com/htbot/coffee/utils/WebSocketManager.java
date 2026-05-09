package com.htbot.coffee.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htbot.coffee.MyApplication;
import com.htbot.coffee.ui.MainActivity;
import com.htbot.coffee.ui.PayActivity;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketManager extends WebSocketListener {
    private final String TAG = WebSocketManager.class.getSimpleName();
    public static final WebSocketManager INSTANCE = new WebSocketManager();
    private final AtomicBoolean connectState = new AtomicBoolean(false);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
    private String url;
    private OkHttpClient client;
    private WebSocket webSocket;
    private boolean stop = false;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final PublishSubject<Map<String, String>> publishSubject = PublishSubject.create();
    private final PublishSubject<Map<String, String>> publishSubjectMain = PublishSubject.create();

    private long lastReceiveTime = 0L;
    private String lastQrContent = "";

    private final Runnable connectRun = new Runnable() {
        @Override
        public void run() {
            WebSocketManager.this.connect();
            WebSocketManager.this.mainHandler.postDelayed(this, 3000L);
        }
    };

    public void regeditTokenRefresh(Consumer<Map<String, String>> consumer) {
        this.compositeDisposable.add(publishSubject.subscribe(consumer));
    }

    public void regeditGoodRefresh(Consumer<Map<String, String>> consumer) {
        this.compositeDisposable.add(publishSubjectMain.subscribe(consumer));
    }


    public void init(String url) {
        this.url = url;
        this.client = new OkHttpClient.Builder().pingInterval(20, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
        this.mainHandler.post(connectRun);
    }

    public void addInitDispose(Disposable disposable) {
        this.compositeDisposable.add(disposable);
    }

    private void onMessage(String msg) {
        Map<String, String> msgContent = gson.fromJson(msg, new TypeToken<Map<String, String>>() {
        });
        if (msgContent.containsKey("method") && Objects.equals(msgContent.get("method"), "token")) {
            String token = msgContent.get("token");
            if (publishSubject.hasObservers() && token != null && !token.isEmpty()) {
                publishSubject.onNext(msgContent);
            }
        }
        if (msgContent.containsKey("method") && Objects.equals(msgContent.get("method"), "transmit")) {
            if (publishSubjectMain.hasObservers()) {
                publishSubjectMain.onNext(msgContent);
            }
        }
        if (msgContent.containsKey("method") && Objects.equals(msgContent.get("method"), "qrResult")) {
            Activity topActivity = ActivityUtils.getTopActivity();
            String content = msgContent.get("content");

            if (TextUtils.isEmpty(content) || topActivity == null) {
                return;
            }

            boolean isJson = isJsonObject(content);

            // MainActivity：只接收 JSON 类型
            if (topActivity instanceof MainActivity) {
                if (!isJson) {
                    Log.d(TAG, "MainActivity ignore string pay code: " + content);
                    return;
                }
            }
            // PayActivity：只接收 String 类型
            else if (topActivity instanceof PayActivity) {
                if (isJson) {
                    Log.d(TAG, "PayActivity ignore json coupon code: " + content);
                    return;
                }
            } else {
                // 其他页面不处理
                return;
            }

            long now = System.currentTimeMillis();
            if (content.equals(lastQrContent) && now - lastReceiveTime < 15000) {
                Log.d(TAG, "duplicate qrResult ignored: " + content);
                return;
            }

            Intent intent = new Intent("WEBSOCKET_MESSAGE_ACTION");
            intent.setPackage(MyApplication.instance.getPackageName());
            intent.putExtra("payCode", content);
            MyApplication.instance.getApplicationContext().sendBroadcast(intent);

            lastQrContent = content;
            lastReceiveTime = now;
        }
    }

    private boolean isJsonObject(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        String trim = text.trim();
        return trim.startsWith("{") && trim.endsWith("}");
    }

    public synchronized void connect() {
        if (this.stop) {
            return;
        }
        if (this.connectState.get()) {
            Log.d(TAG, "heart log ");
            return;
        }
        try {
            if (webSocket != null) {
                this.webSocket.close(1001, "reconnect");
            }
        } catch (Exception e) {
            Log.d(TAG, "close error" + e);
        }
        try {
            Log.d(TAG, "heart init");
            this.webSocket = this.client.newWebSocket(new Request.Builder().url(this.url).build(), this);
        } catch (Exception e) {
            Log.d(TAG, "connect error" + e);
        }
    }

    public void disconnect() {
        this.stop = true;
        try {
            if (webSocket != null && this.connectState.get()) {
                this.webSocket.close(1000, "disconnect");
            }
        } catch (Exception e) {
            Log.d(TAG, "close error" + e);
        }
        this.mainHandler.removeCallbacks(connectRun);
        this.compositeDisposable.clear();
    }

    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        connectState.set(true);
        Log.d(TAG, "onOpen");
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        Log.d(TAG, "onMessage," + text);
        WebSocketManager.this.onMessage(text);
    }

    @Override
    public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
        connectState.set(false);
        Log.d(TAG, "onFailure," + t);
    }

    @Override
    public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        connectState.set(false);
        Log.d(TAG, "onClosed,code=" + code + ",r=" + reason);
    }

    /**
     * send data
     */
    public void send(String message) {
        if (connectState.get() && webSocket != null) {
            webSocket.send(message);
        }
    }
}
