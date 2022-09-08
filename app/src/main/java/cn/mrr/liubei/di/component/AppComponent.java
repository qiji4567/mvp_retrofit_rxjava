package cn.mrr.liubei.di.component;

import javax.inject.Singleton;

import android.content.Context;

import cn.mrr.liubei.module.ApplicationModule;
import cn.mrr.liubei.module.HttpModule;
import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by xiarh on 2017/9/21.
 */

@Singleton
@Component(modules = {ApplicationModule.class, HttpModule.class})
public interface AppComponent {

    Context getContext(); // 提供Context给子Component使用

//    SharePrefManager getSharePrefManager();

    Retrofit.Builder getRetrofitBuilder();

    OkHttpClient getOkHttpClient();


}