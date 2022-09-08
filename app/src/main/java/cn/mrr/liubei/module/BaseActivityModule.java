package cn.mrr.liubei.module;

import android.app.Activity;


import cn.mrr.liubei.di.qualifier.WeatherURL;
import cn.mrr.liubei.di.scope.ActivityScope;
import cn.mrr.liubei.net.IdeaApiService;
import cn.mrr.liubei.net.ServerConfig;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiarh on 2017/10/10.
 */

@Module
public class BaseActivityModule {

    private Activity activity;

    public BaseActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return activity;
    }

//    @Provides
//    @ActivityScope
//    RxPermissions provideRxPermissions(Activity activity) {
//        return new RxPermissions(activity);
//    }

    @WeatherURL
    @Provides
    @ActivityScope
    Retrofit provideWeatherRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return builder
                .baseUrl(ServerConfig.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ActivityScope
    IdeaApiService provideWeatherApi(@WeatherURL Retrofit retrofit) {
        return retrofit.create(IdeaApiService.class);
    }
}
