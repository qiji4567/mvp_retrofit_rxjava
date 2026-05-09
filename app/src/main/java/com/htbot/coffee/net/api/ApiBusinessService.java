package com.htbot.coffee.net.api;

import com.htbot.coffee.mvp.module.DeviceInfoBean;
import com.htbot.coffee.net.ResponseData;


import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiBusinessService {

    @POST("/gw/v4/good/deviceGood/menuGroupList")
    Observable<ResponseData> getMenuGroupList(@Body RequestBody body);

    @POST("/gw/v4/order/detail/submitBarCodePayOrder")
    Observable<ResponseData> submitPayOrder(@Body RequestBody body);

    @POST("/gw/v4/order/detail/submitPosPayOrder")
    Observable<ResponseData> submitPosPayOrder(@Body RequestBody body);

    @POST("/gw/v4/good/deviceGood/printList")
    Observable<ResponseData> getPrintList(@Body RequestBody body);

    @POST("/gw/v4/good/order/goodList")
    Observable<ResponseData> getGoodInfo(@Body RequestBody body);

    @POST("/gw/v4/good/order/materialList")
    Observable<ResponseData> getMaterialList(@Body RequestBody body);

    @POST("/gw/v4/good/order/matchGood")
    Observable<ResponseData> getMatchGood(@Body RequestBody body);

    @POST("/gw/v4/order/pay/queryPayType")
    Observable<ResponseData> getPayType(@Body RequestBody body);

    @POST("/gw/v4/order/detail/create")
    Observable<ResponseData> createOrder(@Body RequestBody body);

    @POST("/gw/v4/order/detail/createNew")
    Observable<ResponseData> createOrderNew(@Body RequestBody body);

    @POST("/gw/v4/order/pay/query")
    Observable<ResponseData> queryPay(@Body RequestBody body);

    @POST("/gw/v4/good/order/goodSales")
    Observable<ResponseData> queryGoodsStatus(@Body RequestBody body);

    @POST("/gw/v4/order/detail/orderToken")
    Observable<ResponseData> orderToken(@Body RequestBody body);

    @POST("/gw/v4/order/detail/queryQueue")
    Observable<ResponseData> queryQueue(@Body RequestBody body);

    @POST("/gw/v4/order/detail/cancelOrder")
    Observable<ResponseData> cancelOrder(@Body RequestBody body);

    @POST("/gw/v4/good/devops/deviceEventRecord")
    Observable<ResponseData> deviceEventRecord(@Body RequestBody body);

    @POST("/gw/v4/user/device/detail")
    Observable<ResponseData<DeviceInfoBean>> getDeviceDetail(@Body RequestBody body);

    @POST("/gw/v4/user/login/operaAnyOne")
    Observable<ResponseData> deviceOperaAnyOne(@Body RequestBody body);

    @POST("/gw/v4/mini/active/verifyExCoupon")
    Observable<ResponseData> verifyExCoupon(@Body RequestBody body);

    @POST("/gw/v4/mini/active/useExCoupon")
    Observable<ResponseData> useExCoupon(@Body RequestBody body);
}