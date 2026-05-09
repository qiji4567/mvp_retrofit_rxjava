package com.htbot.coffee.net.api;

import com.htbot.coffee.net.ApiClient;
import com.htbot.coffee.net.ResponseData;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

/**
 * @author 53443
 */
public final class OperationApi {

    private OperationApi() {
    }

    public static Observable<ResponseData> login(RequestBody body) {
        return ApiClient.operationService().login(body);
    }

    public static Observable<ResponseData<String>> getPublicKey() {
        return ApiClient.operationService().getPublicKey();
    }

    public static Observable<ResponseData> getDeviceDetailNoToast(String id) {
        return ApiClient.operationService().getDeviceDetail(id);
    }

    public static Observable<ResponseData> getDeviceDetail(String id) {
        return ApiClient.operationService().getDeviceDetail(id);
    }

    public static Observable<ResponseData> deviceOperationStatusUpdate(RequestBody body) {
        return ApiClient.operationService().deviceOperationStatusUpdate(body);
    }

    public static Observable<ResponseData> command(RequestBody body) {
        return ApiClient.operationService().command(body);
    }

    public static Observable<ResponseData> getEquipmentBin(String deviceId) {
        return ApiClient.operationService().getEquipmentBin(deviceId);
    }

    public static Observable<ResponseData> getMakingOrder(RequestBody body) {
        return ApiClient.operationService().getMakingOrder(body);
    }

    public static Observable<ResponseData> remakeMakingOrder(RequestBody body) {
        return ApiClient.operationService().remakeMakingOrder(body);
    }

    public static Observable<ResponseData> ignoreMakingOrder(RequestBody body) {
        return ApiClient.operationService().ignoreMakingOrder(body);
    }

    public static Observable<ResponseData> addMaterial(RequestBody body) {
        return ApiClient.operationService().addMaterial(body);
    }

    public static Observable<ResponseData> updateMaterial(RequestBody body) {
        return ApiClient.operationService().updateMaterial(body);
    }

    public static Observable<ResponseData> updateTare(RequestBody body) {
        return ApiClient.operationService().updateTare(body);
    }

    public static Observable<ResponseData> deviceRestart(RequestBody body) {
        return ApiClient.operationService().deviceRestart(body);
    }

    public static Observable<ResponseData> deviceUpdate(String id) {
        return ApiClient.operationService().deviceUpdate(id);
    }

    public static Observable<ResponseData> logout(RequestBody body) {
        return ApiClient.operationService().logout(body);
    }

    public static Observable<ResponseData> cleanTypeList(RequestBody body) {
        return ApiClient.operationService().cleanTypeList(body);
    }

    public static Observable<ResponseData> getUserDetail(String lang) {
        return ApiClient.operationService().getUserDetail(lang);
    }

    public static Observable<ResponseData> deviceCleaningSubmit(RequestBody body) {
        return ApiClient.operationService().deviceCleaningSubmit(body);
    }

    public static Observable<ResponseData> deviceCleaningPage(RequestBody body) {
        return ApiClient.operationService().deviceCleaningPage(body);
    }
}