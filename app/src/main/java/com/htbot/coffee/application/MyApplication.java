package com.htbot.coffee.application;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.htbot.coffee.R;
import com.htbot.coffee.api.BusinessApi;
import com.htbot.coffee.entity.DeviceInfoBean;
import com.htbot.coffee.utils.AESUtils;
import com.htbot.coffee.utils.DeviceInfoUtil;
import com.htbot.coffee.utils.GrpcManager;
import com.htbot.coffee.utils.GsonUtils;
import com.htbot.coffee.utils.LogUtils;
import com.htbot.coffee.utils.MultiLanguageUtil;
import com.htbot.coffee.utils.WebSocketManager;
import com.htbot.coffee.utils.request.HttpClient;
import com.htbot.coffee.utils.request.NetworkDialogManager;

import java.util.HashMap;
import java.util.Map;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.RequestBody;

public class MyApplication extends Application {
    public String serialNumber = "";
    public Integer equipmentId;
    public Integer corpId;
    public static int themeEvent = 1000;
    public static int menuEvent = 1001;
    //服务器地址
    public String Base_URL = "";
    public String TOKEN = "";
    public Boolean queryDeviceFailed = false;

    public static MyApplication instance = null;

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public Integer getCorpId() {
        return corpId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getBase_URL() {
        return Base_URL;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    // 应用创建时调用，初始化应用实例
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        NetworkDialogManager.getInstance(this).registerNetworkCallback();
        ToastUtils.getDefaultMaker().setBgResource(R.drawable.bubble_bg);
        ToastUtils.getDefaultMaker().setTextSize(24);
        SPUtils.getInstance().put("adminToken", "");
        SPUtils.getInstance().put("passwordless", "");
        DeviceInfoUtil.removeAll();
        MultiLanguageUtil.getInstance().init(this);
        try {
            final String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            WebSocketManager.INSTANCE.regeditTokenRefresh(msgContent -> {
                MyApplication.instance.serialNumber = msgContent.get("deviceId");
                MyApplication.instance.TOKEN = msgContent.get("token");
                MyApplication.instance.Base_URL = msgContent.get("cloud");
                RetrofitUrlManager.getInstance().setGlobalDomain(MyApplication.instance.Base_URL);

                Map<String, String> versionMsg = new HashMap<>();
                versionMsg.put("version", version);
                versionMsg.put("method", "report_version");
                WebSocketManager.INSTANCE.send(new Gson().toJson(versionMsg));

                HashMap<String, Object> postMap = new HashMap<>();
                postMap.put("serialNumber", MyApplication.instance.serialNumber);
                LogUtils.d("打印appliction：", GsonUtils.toJson(postMap));
                RequestBody body = RequestBody.create(HttpClient.mediaType, AESUtils.createRequestBody(postMap));
                WebSocketManager.INSTANCE.addInitDispose(BusinessApi.getDeviceDetail(body)
                        .doOnError(error -> {
                            MyApplication.instance.queryDeviceFailed = true;
                        }).subscribe(
                                responseData -> {
                                    if (responseData.getSuccess()) {
                                        DeviceInfoBean deviceInfoBean = new Gson().fromJson(responseData.getData(), DeviceInfoBean.class);
                                        DeviceInfoUtil.setDeviceInfo(deviceInfoBean);
                                        MyApplication.instance.equipmentId = deviceInfoBean.getId();
                                        MyApplication.instance.corpId = deviceInfoBean.getOrgId();
                                    }
                                }, throwable -> {
                                    ToastUtils.showLong(throwable.getMessage());
                                })
                );
            });
            WebSocketManager.INSTANCE.init("ws://192.168.2.100:6000");
            GrpcManager.getInstance().start();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("MyApplication", "Failed to get package info: " + e.getMessage());
        } catch (Exception e) {
            Log.e("MyApplication", "Failed: " + e.getMessage());
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        WebSocketManager.INSTANCE.disconnect();
    }
}
