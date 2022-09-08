package cn.mrr.liubei.module;

import javax.inject.Singleton;

import android.content.Context;

import cn.mrr.liubei.application.MyApplication;
import cn.mrr.liubei.di.qualifier.WeatherURL;
import cn.mrr.liubei.net.ServerConfig;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiarh on 2017/9/21.
 */

@Module
public class ApplicationModule {

    private final MyApplication application;

    public ApplicationModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

//    @Provides
//    @Singleton
//    SharePrefManager provideSharePrefManager(Context context) {
//        return new SharePrefManager(context);
//    }


    @WeatherURL
    @Provides
    @Singleton
    Retrofit provideAppRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return builder
                .baseUrl(ServerConfig.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}