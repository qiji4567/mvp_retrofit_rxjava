package cn.mrr.mvp.application;

import android.app.Application;

import cn.mrr.mvp.network.NetWorkManager;

/**
 * =================================================
 *
 * @author qi ji
 * @date 2021/8/23 11:28
 * @Email: 534438777@qq.com
 * @Content: =================================================
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance().init();
    }
}
 
 