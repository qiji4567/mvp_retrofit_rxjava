package com.htbot.coffee.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {

    private static volatile String baseUrl;
    private static volatile Retrofit retrofit;
    private static volatile ApiService apiService;

    private ApiClient() {
    }

    public static synchronized void init(String url) {
        setBaseUrl(url);
    }

    public static synchronized void setBaseUrl(String url) {
        String finalUrl = normalizeBaseUrl(url);

        if (finalUrl.equals(baseUrl) && retrofit != null && apiService != null) {
            return;
        }

        baseUrl = finalUrl;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            throw new IllegalStateException("ApiClient 未初始化，请先在 MyApplication.onCreate() 调用 ApiClient.init(baseUrl)");
        }
        return apiService;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    private static String normalizeBaseUrl(String url) {
        if (url == null || url.trim().length() == 0) {
            throw new IllegalArgumentException("BaseUrl 不能为空");
        }

        String result = url.trim();

        if (!result.startsWith("http://") && !result.startsWith("https://")) {
            throw new IllegalArgumentException("BaseUrl 必须以 http:// 或 https:// 开头");
        }

        if (!result.endsWith("/")) {
            result = result + "/";
        }

        return result;
    }
}