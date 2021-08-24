package cn.mrr.mvp.network;

import cn.mrr.mvp.network.request.Request;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * =================================================
 *
 * @author qi ji
 * @date 2021/8/23 11:06
 * @Email: 534438777@qq.com
 * @Content: =================================================
 * <p>
 * API初始类
 */
public class NetWorkManager {

    private static NetWorkManager getInstance = null;
    private static Retrofit retrofit = null;
    private static volatile Request request = null;


    /**
     * 单利
     *
     * @return
     */
    public static NetWorkManager getInstance() {
        if (getInstance == null) {
            synchronized (NetWorkManager.class) {
                if (getInstance == null) {
                    getInstance = new NetWorkManager();
                }
            }
        }
        return getInstance;
    }

    /**
     * 初始化必要参数
     */
    public void init() {

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Request.HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }

    /**
     * retrofit添加注解请求网络接口
     * @return
     */
    public static Request getRequest() {
        if (request == null) {
            synchronized (Request.class) {
                if (request == null) {
                    request = retrofit.create(Request.class);
                }
            }
        }
        return request;
    }


} 
 
 