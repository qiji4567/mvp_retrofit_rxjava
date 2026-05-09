package com.htbot.coffee.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        Request newRequest = oldRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                // 以后有 token 可以在这里加：
                // .header("Authorization", "Bearer " + TokenManager.getToken())
                .build();

        return chain.proceed(newRequest);
    }
}