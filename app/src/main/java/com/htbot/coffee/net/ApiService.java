package com.htbot.coffee.net;

import androidx.annotation.Nullable;

import com.htbot.coffee.mvp.MobileCountModel;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login")
    Observable<BaseBean<MobileCountModel>> login(
            @Field("username") @Nullable String username,
            @Field("password") @Nullable String password
    );
}