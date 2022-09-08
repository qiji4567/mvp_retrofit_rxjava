package cn.mvp.network.net.module;


import cn.mvp.network.utils.SPUtils;
import cn.mvp.network.utils.ApplicationContextUtils;

/**
 * Created by jokerlee on 16/9/28.
 */
public class BaseRequest {
    public String token;


    public BaseRequest() {
        token= (String) SPUtils.get(ApplicationContextUtils.getInstance(),"token","");
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void updateToken(String token) {
        this.token = token;
    }
}
