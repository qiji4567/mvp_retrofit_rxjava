package com.htbot.coffee.net.request;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

import com.blankj.utilcode.util.ActivityUtils;
import com.htbot.coffee.dialog.OutLineDialog;

/**
 * @author 53443
 */
public class NetworkDialogManager {

    private static volatile NetworkDialogManager instance;

    private final Context appContext;
    private OutLineDialog outLineDialog;
    private boolean isShowing = false;
    private boolean hasNetwork = true;
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    private NetworkDialogManager(Context context) {
        this.appContext = context.getApplicationContext();
    }

    public static NetworkDialogManager getInstance(Context context) {
        if (instance == null) {
            synchronized (NetworkDialogManager.class) {
                if (instance == null) {
                    instance = new NetworkDialogManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * 初始化网络监听，只需调用一次
     */
    public void registerNetworkCallback() {
        connectivityManager =
                (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) return;

        // 初始化当前网络状态
        hasNetwork = isNetworkAvailable();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    hasNetwork = true;
                    dismissDialog();
                }

                @Override
                public void onLost(Network network) {
                    hasNetwork = isNetworkAvailable();
                    if (!hasNetwork) {
                        showDialog();
                    }
                }

                @Override
                public void onUnavailable() {
                    hasNetwork = false;
                    showDialog();
                }
            };
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build();

            networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    hasNetwork = true;
                    dismissDialog();
                }

                @Override
                public void onLost(Network network) {
                    hasNetwork = isNetworkAvailable();
                    if (!hasNetwork) {
                        showDialog();
                    }
                }

                @Override
                public void onUnavailable() {
                    hasNetwork = false;
                    showDialog();
                }
            };
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }

        // 如果启动时就无网，直接弹
        if (!hasNetwork) {
            showDialog();
        }
    }

    public boolean isNetworkAvailable() {
        if (connectivityManager == null) {
            connectivityManager =
                    (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        if (connectivityManager == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities capabilities =
                    connectivityManager.getNetworkCapabilities(network);
            return capabilities != null
                    && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        } else {
            android.net.NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }
    }

    /**
     * 无网时调用
     */
    public synchronized void showDialog() {
        if (isShowing) return;

        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null || topActivity.isFinishing()) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                && topActivity.isDestroyed()) {
            return;
        }

        try {
            if (outLineDialog == null || outLineDialog.getContext() != topActivity) {
                outLineDialog = new OutLineDialog(topActivity);
            }

            if (!outLineDialog.isShowing()) {
                outLineDialog.show();
                isShowing = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 有网时调用
     */
    public synchronized void dismissDialog() {
        try {
            if (outLineDialog != null && outLineDialog.isShowing()) {
                outLineDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isShowing = false;
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    public boolean hasNetwork() {
        return hasNetwork;
    }
}