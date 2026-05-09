package com.htbot.coffee.net.api;

import com.htbot.coffee.mvp.module.DeviceInfoBean;
import com.htbot.coffee.net.ApiClient;
import com.htbot.coffee.net.ResponseData;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

public final class BusinessApi {

    private BusinessApi() {
    }

    public static Observable<ResponseData> getMenuGroupList(RequestBody body) {
        return ApiClient.businessService().getMenuGroupList(body);
    }

    public static Observable<ResponseData> getPrintList(RequestBody body) {
        return ApiClient.businessService().getPrintList(body);
    }

    public static Observable<ResponseData> getGoodInfo(RequestBody body) {
        return ApiClient.businessService().getGoodInfo(body);
    }

    public static Observable<ResponseData> getMaterialList(RequestBody body) {
        return ApiClient.businessService().getMaterialList(body);
    }

    public static Observable<ResponseData> getMatchGood(RequestBody body) {
        return ApiClient.businessService().getMatchGood(body);
    }

    public static Observable<ResponseData> getPayType(RequestBody body) {
        return ApiClient.businessService().getPayType(body);
    }

    public static Observable<ResponseData> createOrder(RequestBody body) {
        return ApiClient.businessService().createOrder(body);
    }

    public static Observable<ResponseData> createOrderNew(RequestBody body) {
        return ApiClient.businessService().createOrderNew(body);
    }

    public static Observable<ResponseData> submitPayOrder(RequestBody body) {
        return ApiClient.businessService().submitPayOrder(body);
    }

    public static Observable<ResponseData> submitPosPayOrder(RequestBody body) {
        return ApiClient.businessService().submitPosPayOrder(body);
    }

    public static Observable<ResponseData> queryPay(RequestBody body) {
        return ApiClient.businessService().queryPay(body);
    }

    public static Observable<ResponseData> queryGoodsStatus(RequestBody body) {
        return ApiClient.businessService().queryGoodsStatus(body);
    }

    public static Observable<ResponseData> orderToken(RequestBody body) {
        return ApiClient.businessService().orderToken(body);
    }

    public static Observable<ResponseData> queryQueue(RequestBody body) {
        return ApiClient.businessService().queryQueue(body);
    }

    public static Observable<ResponseData> cancelOrder(RequestBody body) {
        return ApiClient.businessService().cancelOrder(body);
    }

    public static Observable<ResponseData> deviceEventRecord(RequestBody body) {
        return ApiClient.businessService().deviceEventRecord(body);
    }

    public static Observable<ResponseData<DeviceInfoBean>> getDeviceDetail(RequestBody body) {
        return ApiClient.businessService().getDeviceDetail(body);
    }

    public static Observable<ResponseData> deviceOperaAnyOne(RequestBody body) {
        return ApiClient.businessService().deviceOperaAnyOne(body);
    }

    public static Observable<ResponseData> verifyExCoupon(RequestBody body) {
        return ApiClient.businessService().verifyExCoupon(body);
    }

    public static Observable<ResponseData> useExCoupon(RequestBody body) {
        return ApiClient.businessService().useExCoupon(body);
    }
}