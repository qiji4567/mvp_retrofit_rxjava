package cn.mrr.liubei.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {

    private static final int CONNECT_TIMEOUT_SECONDS = 15;
    private static final int READ_TIMEOUT_SECONDS = 20;
    private static final int WRITE_TIMEOUT_SECONDS = 20;

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

        OkHttpClient okHttpClient = createOkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static ApiService api() {
        if (apiService == null) {
            throw new IllegalStateException("ApiClient 尚未初始化，请先在 MyApplication.onCreate() 中调用 ApiClient.init(baseUrl)");
        }
        return apiService;
    }

    public static Retrofit retrofit() {
        if (retrofit == null) {
            throw new IllegalStateException("ApiClient 尚未初始化，请先在 MyApplication.onCreate() 中调用 ApiClient.init(baseUrl)");
        }
        return retrofit;
    }

    private static OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();
    }

    private static String normalizeBaseUrl(String url) {
        if (url == null || url.trim().length() == 0) {
            throw new IllegalArgumentException("BaseUrl 不能为空");
        }

        String result = url.trim();

        if (!result.startsWith("http://") && !result.startsWith("https://")) {
            throw new IllegalArgumentException("BaseUrl 必须以 http:// 或 https:// 开头：" + result);
        }

        if (!result.endsWith("/")) {
            result = result + "/";
        }

        return result;
    }
}