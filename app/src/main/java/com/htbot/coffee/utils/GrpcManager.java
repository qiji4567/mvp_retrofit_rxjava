package com.htbot.coffee.utils;

import android.util.Log;

import com.htbot.coffee.MyApplication;
import com.htbot.coffee.order.grpc.Message;
import com.htbot.coffee.order.grpc.MessageServiceGrpc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;

public class GrpcManager extends MessageServiceGrpc.MessageServiceImplBase {
    private static volatile GrpcManager instance;

    public static GrpcManager getInstance() {
        if (instance == null) {
            synchronized (GrpcManager.class) {
                if (instance == null) {
                    instance = new GrpcManager();
                }
            }
        }
        return instance;
    }

    private volatile boolean starting = false;
    private Server server;
    private StreamObserver<Message> streamObserver;
    private final ScheduledExecutorService schedulerExecutor = Executors.newSingleThreadScheduledExecutor();

    @Override
    public StreamObserver<Message> chatStream(StreamObserver<Message> responseObserver) {
        streamObserver = responseObserver;
        return new StreamObserver<Message>() {
            @Override
            public void onNext(Message value) {
                Log.d(value.getType(), value.getText());
                if (Objects.equals(value.getType(), "init")) {
                    sendLanguageMsg();
                }
            }

            @Override
            public void onError(Throwable t) {
                if (streamObserver != null) {
                    streamObserver = null;
                }
                LogUtils.w(t.getMessage());
            }

            @Override
            public void onCompleted() {
                if (streamObserver != null) {
                    streamObserver = null;
                }
                Log.d("GrpcManager", "onCompleted");
            }
        };
    }

    public static void sendLanguageMsg() {
        try {
            String languageText = LanguageUtil.getSavedLanguage(MyApplication.instance.getApplicationContext());
            GrpcManager.getInstance().sendMessage("language", languageText);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }

    public void sendMessage(String type, String message) {
        if (streamObserver != null) {
            streamObserver.onNext(Message.newBuilder().setType(type).setText(message).build());
        }
    }

    private void broadcast() {
        if (!starting) {
            return;
        }
        if (streamObserver == null) {
            try (DatagramSocket datagramSocket = new DatagramSocket()) {
                datagramSocket.setBroadcast(true);
                byte[] bytes = new byte[]{1};
                InetAddress broadcastAddress = InetAddress.getByName("192.168.2.255");
                datagramSocket.send(new DatagramPacket(bytes, bytes.length, broadcastAddress, 30000));
            } catch (IOException ignored) {
            }
        }
    }

    public void start() throws IOException {
        if (starting) {
            return;
        }
        server = Grpc.newServerBuilderForPort(9999, InsecureServerCredentials.create())
                .addService(this).build()
                .start();
        starting = true;
        schedulerExecutor.scheduleWithFixedDelay(this::broadcast, 0, 10, TimeUnit.SECONDS);
    }

    public void stop() {
        if (!starting) {
            return;
        }
        schedulerExecutor.shutdown();
        server.shutdown();
        starting = false;
    }
}