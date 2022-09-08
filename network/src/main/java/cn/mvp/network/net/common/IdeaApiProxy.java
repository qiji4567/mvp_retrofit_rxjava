package cn.mvp.network.net.common;


import cn.mvp.network.net.token.IGlobalManager;
import cn.mvp.network.net.token.ProxyHandler;

import java.lang.reflect.Proxy;


/**
 * Created by qiji on 2017/4/1.
 */

public class IdeaApiProxy {

    public <T> T getApiService(Class<T> tClass,String baseUrl,IGlobalManager manager) {
        T t = RetrofitService.getRetrofitBuilder(baseUrl)
                .build().create(tClass);
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[] { tClass }, new ProxyHandler(t, manager,baseUrl));
    }
}
