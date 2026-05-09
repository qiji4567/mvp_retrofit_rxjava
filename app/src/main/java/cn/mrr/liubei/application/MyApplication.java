package cn.mrr.liubei;

import android.app.Application;

import cn.mrr.liubei.net.ApiClient;

public class MyApplication extends Application {

    private static MyApplication instance;

    /**
     * 默认 BaseUrl。
     * 注意：Retrofit 的 baseUrl 必须以 / 结尾。
     */
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