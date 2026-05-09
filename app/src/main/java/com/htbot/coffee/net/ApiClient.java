package com.htbot.coffee.net;

import com.htbot.coffee.net.api.ApiBusinessService;
import com.htbot.coffee.net.api.ApiOperationService;

import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 53443
 */
public final class ApiClient {

    private static volatile String baseUrl;
    private static volatile Retrofit retrofit;

    private static volatile ApiBusinessService businessService;
    private static volatile ApiOperationService operationService;

    private ApiClient() {
    }

    public static synchronized void init(String url) {
        setBaseUrl(url);
    }

    public static synchronized void setBaseUrl(String url) {
        String finalUrl = normalizeBaseUrl(url);

        if (finalUrl.equals(baseUrl)
                && retrofit != null
                && businessService != null
                && operationService != null) {
            return;
        }

        baseUrl = finalUrl;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message ->
                com.htbot.coffee.utils.LogUtils.d("OkHttp", message)
        );
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        businessService = retrofit.create(ApiBusinessService.class);
        operationService = retrofit.create(ApiOperationService.class);
    }

    public static ApiBusinessService businessService() {
        if (businessService == null) {
            throw new IllegalStateException("ApiClient 未初始化，请先在 MyApplication.onCreate() 调用 ApiClient.init(baseUrl)");
        }
        return businessService;
    }

    public static ApiOperationService operationService() {
        if (operationService == null) {
            throw new IllegalStateException("ApiClient 未初始化，请先在 MyApplication.onCreate() 调用 ApiClient.init(baseUrl)");
        }
        return operationService;
    }

    public static Retrofit retrofit() {
        if (retrofit == null) {
            throw new IllegalStateException("ApiClient 未初始化，请先在 MyApplication.onCreate() 调用 ApiClient.init(baseUrl)");
        }
        return retrofit;
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