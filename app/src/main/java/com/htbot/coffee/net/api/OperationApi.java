package com.htbot.coffee.net.api;

import com.htbot.coffee.net.ResponseData;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

interface ApiOperationService {
    /**
     * 登录
     */
    @POST("/gw/v1/user/login/login")
    Observable<ResponseData> login(@Body RequestBody body);

    /**
     * 登录
     */
    @GET("/gw/v1/user/login/getRsaPublicKey")
    Observable<ResponseData> getPublicKey();

    /**
     * 获取设备信息
     *
     * @param id
     * @return
     */
    @GET("/gw/v1/user/device/detail")
    Observable<ResponseData> getDeviceDetail(@Query("id") String id);


    /**
     * 运营开关
     *
     * @param body
     * @return
     */
    @PUT("/gw/v1/user/devops/deviceOperationStatusUpdate")
    Observable<ResponseData> deviceOperationStatusUpdate(@Body RequestBody body);

    /**
     * 指令操作
     *
     * @param body
     * @return
     */
    @POST("/gw/v1/good/devops/command")
    Observable<ResponseData> command(@Body RequestBody body);

    /**
     * 获取设备料仓数据
     *
     * @param deviceId
     * @return
     */
    @GET("/gw/v1/good/deviceBin/list")
    Observable<ResponseData> getEquipmentBin(@Query("deviceId") String deviceId);

    /**
     * 查询制作订单
     *
     * @param body
     * @return
     */
    @POST("/gw/v1/order/detail/queryFail")
    Observable<ResponseData> getMakingOrder(@Body RequestBody body);

    /**
     * 重做
     *
     * @param body
     * @return
     */
    @POST("/gw/v1/order/detail/remakeBatch")
    Observable<ResponseData> remakeMakingOrder(@Body RequestBody body);

    /**
     * 忽略
     *
     * @param body
     * @return
     */
    @POST("/gw/v1/order/detail/ignoreBatch")
    Observable<ResponseData> ignoreMakingOrder(@Body RequestBody body);

    /**
     * 加豆
     *
     * @param body
     * @return
     */
    @POST("/gw/v1/good/deviceBin/addMaterial")
    Observable<ResponseData> addMaterial(@Body RequestBody body);

    /**
     * 更新豆仓
     *
     * @param body
     * @return
     */
    @POST("/gw/v1/good/deviceBin/update")
    Observable<ResponseData> updateMaterial(@Body RequestBody body);


    /**
     * 修改皮重
     *
     * @param body
     * @return
     */
    @POST("/gw/v1/good/deviceBin/updateTare")
    Observable<ResponseData> updateTare(@Body RequestBody body);


    /**
     * 重启luban
     *
     * @param body
     * @return
     */
    @POST("/gw/v1/user/devops/deviceRestart")
    Observable<ResponseData> deviceRestart(@Body RequestBody body);


    /**
     * 获取设备信息
     *
     * @param id
     * @return
     */
    @GET("/gw/v1/user/devops/deviceUpdate")
    Observable<ResponseData> deviceUpdate(@Query("id") String id);

    /**
     * 退出登录
     *
     * @return
     */
    @POST("/gw/v1/user/login/logout")
    Observable<ResponseData> logout(@Body RequestBody body);


    /**
     * 获取用户详情
     */
    @GET("/gw/v1/user/user/detail")
    Observable<ResponseData> getUserDetail(@Query("lang") String lang);

    /**
     * 提交打卡记录
     *
     * @return
     */
    @POST("/gw/v1/good/deviceCleaning/submit")
    Observable<ResponseData> deviceCleaningSubmit(@Body RequestBody body);
    /**
     * 获取清洗类型列表
     *
     * @return
     */
    @POST("/gw/v1/good/deviceCleaning/cleanTypeList")
    Observable<ResponseData> cleanTypeList(@Body RequestBody body);
    /**
     * 分页查询打卡记录
     *
     * @return
     */
    @POST("/gw/v1/good/deviceCleaning/page")
    Observable<ResponseData> deviceCleaningPage(@Body RequestBody body);

}

public class OperationApi {

    static private final ApiOperationService API_OPERATION_SERVICE = HttpRequest.getRetrofit().create(ApiOperationService.class);

    /**
     * 登录
     */
    static public Observable<ResponseData> login(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.login(body));
    }

    static public Observable<ResponseData> getPublicKey() {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.getPublicKey());
    }

    static public Observable<ResponseData> getDeviceDetailNoToast(String id) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.getDeviceDetail(id));
    }

    static public Observable<ResponseData> getDeviceDetail(String id) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.getDeviceDetail(id));
    }

    static public Observable<ResponseData> deviceOperationStatusUpdate(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.deviceOperationStatusUpdate(body));
    }

    static public Observable<ResponseData> command(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.command(body));
    }

    static public Observable<ResponseData> getEquipmentBin(String deviceId) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.getEquipmentBin(deviceId));
    }

    static public Observable<ResponseData> getMakingOrder(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.getMakingOrder(body));
    }

    static public Observable<ResponseData> remakeMakingOrder(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.remakeMakingOrder(body));
    }

    static public Observable<ResponseData> ignoreMakingOrder(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.ignoreMakingOrder(body));
    }

    static public Observable<ResponseData> addMaterial(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.addMaterial(body));
    }

    static public Observable<ResponseData> updateMaterial(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.updateMaterial(body));
    }

    static public Observable<ResponseData> updateTare(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.updateTare(body));
    }

    static public Observable<ResponseData> deviceRestart(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.deviceRestart(body));
    }

    static public Observable<ResponseData> deviceUpdate(String id) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.deviceUpdate(id));
    }

    static public Observable<ResponseData> logout(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.logout(body));
    }

    static public Observable<ResponseData> cleanTypeList(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.cleanTypeList(body));
    }

    static public Observable<ResponseData> getUserDetail(String lang) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.getUserDetail(lang));
    }
    static public Observable<ResponseData> deviceCleaningSubmit(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.deviceCleaningSubmit(body));
    }
    static public Observable<ResponseData> deviceCleaningPage(RequestBody body) {
        return HttpRequestUtil.getApiV1(API_OPERATION_SERVICE.deviceCleaningPage(body));
    }
}
