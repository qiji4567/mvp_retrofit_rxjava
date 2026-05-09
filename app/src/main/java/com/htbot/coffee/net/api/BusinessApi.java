package com.htbot.coffee.net.api;

import com.htbot.coffee.util.request.HttpRequest;
import com.htbot.coffee.util.request.HttpRequestUtil;
import com.htbot.coffee.util.request.ResponseData;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface ApiBusinessService {
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
    Observable<ResponseData> getDeviceDetail(@Body RequestBody body);

    @POST("/gw/v4/user/login/operaAnyOne")
    Observable<ResponseData> deviceOperaAnyOne(@Body RequestBody body);

    /**
     * 核验优惠券（验证标记使用中）
     */
    @POST("/gw/v4/mini/active/verifyExCoupon")
    Observable<ResponseData> verifyExCoupon(@Body RequestBody body);

    /**
     * 用券（下单成功标记已使用）
     */
    @POST("/gw/v4/mini/active/useExCoupon")
    Observable<ResponseData> useExCoupon(@Body RequestBody body);
}

public class BusinessApi {

    private static final ApiBusinessService apiBusinessService =
            HttpRequest.getRetrofit().create(ApiBusinessService.class);

    public static Observable<ResponseData> getMenuGroupList(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.getMenuGroupList(body));
    }

    public static Observable<ResponseData> getPrintList(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.getPrintList(body));
    }

    public static Observable<ResponseData> getGoodInfo(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.getGoodInfo(body));
    }

    public static Observable<ResponseData> getMaterialList(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.getMaterialList(body));
    }

    public static Observable<ResponseData> getMatchGood(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.getMatchGood(body));
    }

    public static Observable<ResponseData> getPayType(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.getPayType(body));
    }

    public static Observable<ResponseData> createOrder(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.createOrder(body));
    }

    public static Observable<ResponseData> createOrderNew(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.createOrderNew(body));
    }

    public static Observable<ResponseData> submitPayOrder(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.submitPayOrder(body));
    }

    public static Observable<ResponseData> submitPosPayOrder(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.submitPosPayOrder(body));
    }

    public static Observable<ResponseData> queryPay(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.queryPay(body));
    }

    public static Observable<ResponseData> queryGoodsStatus(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.queryGoodsStatus(body));
    }

    public static Observable<ResponseData> orderToken(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.orderToken(body));
    }

    public static Observable<ResponseData> queryQueue(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.queryQueue(body));
    }

    public static Observable<ResponseData> cancelOrder(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.cancelOrder(body));
    }

    public static Observable<ResponseData> deviceEventRecord(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.deviceEventRecord(body));
    }

    public static Observable<ResponseData> getDeviceDetail(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.getDeviceDetail(body));
    }

    public static Observable<ResponseData> deviceOperaAnyOne(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.deviceOperaAnyOne(body));
    }

    // ==================== 兑换券 ====================

    public static Observable<ResponseData> verifyExCoupon(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.verifyExCoupon(body));
    }

    public static Observable<ResponseData> useExCoupon(RequestBody body) {
        return HttpRequestUtil.getApiV4(apiBusinessService.useExCoupon(body));
    }
}