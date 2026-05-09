package com.htbot.coffee.application;

import android.app.Application;

import com.htbot.coffee.net.ApiClient;

public class MyApplication extends Application {

    private static MyApplication instance;

    private static final String DEFAULT_BASE_URL = "https://gateway-uat.bestgenetics.com.cn/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ApiClient.init(DEFAULT_BASE_URL);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static void setBaseUrl(String baseUrl) {
        ApiClient.setBaseUrl(baseUrl);
    }

    public static String getBaseUrl() {
        return ApiClient.getBaseUrl();
    }
}