package com.htbot.coffee.net.api;

import com.htbot.coffee.net.ResponseData;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiOperationService {

    @POST("/gw/v1/user/login/login")
    Observable<ResponseData<String>> login(@Body RequestBody body);

    @GET("/gw/v1/user/login/getRsaPublicKey")
    Observable<ResponseData<String>> getPublicKey();

    @GET("/gw/v1/user/device/detail")
    Observable<ResponseData> getDeviceDetail(@Query("id") String id);

    @PUT("/gw/v1/user/devops/deviceOperationStatusUpdate")
    Observable<ResponseData> deviceOperationStatusUpdate(@Body RequestBody body);

    @POST("/gw/v1/good/devops/command")
    Observable<ResponseData> command(@Body RequestBody body);

    @GET("/gw/v1/good/deviceBin/list")
    Observable<ResponseData> getEquipmentBin(@Query("deviceId") String deviceId);

    @POST("/gw/v1/order/detail/queryFail")
    Observable<ResponseData> getMakingOrder(@Body RequestBody body);

    @POST("/gw/v1/order/detail/remakeBatch")
    Observable<ResponseData> remakeMakingOrder(@Body RequestBody body);

    @POST("/gw/v1/order/detail/ignoreBatch")
    Observable<ResponseData> ignoreMakingOrder(@Body RequestBody body);

    @POST("/gw/v1/good/deviceBin/addMaterial")
    Observable<ResponseData> addMaterial(@Body RequestBody body);

    @POST("/gw/v1/good/deviceBin/update")
    Observable<ResponseData> updateMaterial(@Body RequestBody body);

    @POST("/gw/v1/good/deviceBin/updateTare")
    Observable<ResponseData> updateTare(@Body RequestBody body);

    @POST("/gw/v1/user/devops/deviceRestart")
    Observable<ResponseData> deviceRestart(@Body RequestBody body);

    @GET("/gw/v1/user/devops/deviceUpdate")
    Observable<ResponseData> deviceUpdate(@Query("id") String id);

    @POST("/gw/v1/user/login/logout")
    Observable<ResponseData> logout(@Body RequestBody body);

    @GET("/gw/v1/user/user/detail")
    Observable<ResponseData> getUserDetail(@Query("lang") String lang);

    @POST("/gw/v1/good/deviceCleaning/submit")
    Observable<ResponseData> deviceCleaningSubmit(@Body RequestBody body);

    @POST("/gw/v1/good/deviceCleaning/cleanTypeList")
    Observable<ResponseData> cleanTypeList(@Body RequestBody body);

    @POST("/gw/v1/good/deviceCleaning/page")
    Observable<ResponseData> deviceCleaningPage(@Body RequestBody body);
}