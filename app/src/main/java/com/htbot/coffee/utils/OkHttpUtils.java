package com.htbot.coffee.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {
    private OkHttpClient mOkHttpClient = null;
    private static OkHttpUtils mOkhttpUtils = null;
    private Context mContext;
    public static final long timeout = 30; // OkHttp网络超时时间30秒

    /**
     * 构造方法私有化
     */
    private OkHttpUtils() {
        // 创建okhttp对象 以及连接, 读, 取超时时间
        mOkHttpClient =
                RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                        .connectTimeout(timeout, TimeUnit.SECONDS) // 连接时间
                        .readTimeout(timeout, TimeUnit.SECONDS)    // 读时间
                        .writeTimeout(timeout, TimeUnit.SECONDS)   // 写时间
                        .build();
    }

    /**
     * 获取此单例类对象的方法
     */
    public static OkHttpUtils getInstance() {
        if (null == mOkhttpUtils) { // 单例对象为空
            synchronized (OkHttpUtils.class) {
                mOkhttpUtils = new OkHttpUtils();
            }
        }
        return mOkhttpUtils;
    }

    /**
     * GET 请求
     */
    public void requestGet(String url, CallBack callBack) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Log.i("http", "请求地址：" + url);
        try (Response response = mOkHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                Log.i("http", "返回结果：" + responseData);
                callBack.success(responseData);
            } else {
                Log.i("http", response.code() + "");
                callBack.error(response.code() + "");
            }
        } catch (IOException e) {
            callBack.error(e.getMessage() + "");
        }
    }

    /**
     * POST 请求
     */
    public void requestPost(String url, String jsonData, String serialNumber, CallBack callBack) {
        // 计算时间戳
        String timeStamp = getTimeStamp();

        // 计算签名（HMAC-MD5）
        String sig = generateSig(jsonData, serialNumber, timeStamp);

        // 封装请求体内容，按照要求封装成一个JSON对象
//        String requestBodyJson = generateRequestBody(jsonData, serialNumber, timeStamp, sig);

        // 创建RequestBody，用来发送封装后的JSON数据
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), jsonData
        );

        // 构建POST请求
        Request request = new Request.Builder()
                .url(url)
                .post(body) // 使用POST方法并添加RequestBody
//                .addHeader("Authorization", "Authorization:{"+ MyApplication.TOKEN+" }" ) // 添加Authorization头
                .build();

        Log.i("http", "请求地址：" + url);


        try (Response response = mOkHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                Log.i("http", "返回结果：" + responseData);
                callBack.success(responseData);
            } else {
                Log.i("http", response.code() + "");
                callBack.error(response.code() + "");
            }
        } catch (IOException e) {
            callBack.error(e.getMessage());
        }
    }

    /**
     * 封装请求体的 JSON 数据，包括 data, serialNumber, timeStamp, sig
     */
    private String generateRequestBody(String jsonData, String serialNumber, String timeStamp, String sig) {
        // 构建请求参数的 JSON
        String result = "{\"data\": " + AESUtils.encrypt(jsonData) + ", \"serialNumber\": \"" + serialNumber +
                "\", \"timeStamp\": \"" + timeStamp + "\", \"sig\": \"" + sig + "\"}";

        Log.i("http", "请求参数：" + result);
        return result;
    }

    /**
     * 获取当前时间戳，格式为yyyyMMddHHmmss
     */
    private String getTimeStamp() {
        // 获取当前时间戳（按照 GB/T 7408 标准，格式为 yyyyMMddHHmmss）
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new java.util.Date());
    }

    /**
     * 使用HMAC-MD5生成签名
     */
    private String generateSig(String jsonData, String serialNumber, String timeStamp) {
        try {
            // 参数拼接：data=...&serialNumber=...&timeStamp=...
            String data = "serialNumber=" + serialNumber + "&Data=" + jsonData + "&timeStamp=" + timeStamp;

            // 使用HMAC-MD5算法生成签名
            String secretKey = "IWEklmQVNi6rKY4Q";  // 这里替换成你的密钥
            Mac mac = Mac.getInstance("HmacMD5");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacMD5");
            mac.init(secretKeySpec);
            byte[] result = mac.doFinal(data.getBytes());

            // 将签名结果转为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface CallBack {
        void success(String msg);

        void error(String msg);
    }
}
