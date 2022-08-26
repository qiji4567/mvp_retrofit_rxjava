package cn.mrr.liubei.application;

import android.app.Application;

import cn.mrr.liubei.di.component.AppComponent;
import cn.mrr.liubei.di.component.DaggerAppComponent;
import cn.mrr.liubei.module.ApplicationModule;
import cn.mrr.liubei.module.HttpModule;
import cn.mvp.network.utils.ApplicationContextUtils;


/**
 * =================================================
 *
 * @author qi ji
 * @date 2021/8/23 11:28
 * @Email: 534438777@qq.com
 * @Content: =================================================
 */
public class MyApplication extends Application {


    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationContextUtils.init(this);
        appComponent = DaggerAppComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
 
 