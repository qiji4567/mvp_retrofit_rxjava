package com.htbot.coffee.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private static volatile String token;

    public static void setToken(String value) {
        token = value;
    }

    public static void clearToken() {
        token = null;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        Request.Builder builder = oldRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");

        if (token != null && token.length() > 0) {
            builder.header("Authorization", "Bearer " + token);
        }

        return chain.proceed(builder.build());
    }
}