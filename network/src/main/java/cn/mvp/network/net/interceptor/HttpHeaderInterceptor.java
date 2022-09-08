package cn.mvp.network.net.interceptor;

import java.io.IOException;

//import cn.mvp.network.net.module.UserInfoTools;
import cn.mvp.network.net.module.LoginResponse;
import cn.mvp.network.utils.ApplicationContextUtils;
import cn.mvp.network.utils.SPUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qiji on 2022/2/28.
 */

public class HttpHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //  配置请求头
        String accessToken = "";
        LoginResponse response = SPUtils.getObject(ApplicationContextUtils.getInstance(), LoginResponse.class);
        if (response != null && response.getData() != null) {
            accessToken = response.getData().getAccess_token();
        }
//        LogUtils.e("打印qqqq" + response.toString());
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", accessToken)
                .addHeader("Content-Type", "application/json")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept-Encoding", "identity")
                .build();
        return chain.proceed(request);
    }
}
