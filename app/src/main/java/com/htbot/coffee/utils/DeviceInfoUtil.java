package com.htbot.coffee.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.htbot.coffee.entity.DeviceInfoBean;

public class DeviceInfoUtil {

    public static DeviceInfoBean getDeviceInfo() {
        if (StringUtils.isEmpty(SPStaticUtils.getString("deviceInfo"))) {
            return new DeviceInfoBean();
        } else {
            return JSONObject.parseObject(SPStaticUtils.getString("deviceInfo"), DeviceInfoBean.class);
        }
    }

    public static void setDeviceInfo(DeviceInfoBean user) {
        SPStaticUtils.put("deviceInfo", JSON.toJSONString(user));
    }

    public static String getDevicePayType() {
        return SPStaticUtils.getString("corpPayType");
    }

    public static void setDevicePay(String payType) {
        SPStaticUtils.put("corpPayType", payType);
    }

    public static void removeAll() {
        SPStaticUtils.remove("deviceInfo");
        SPStaticUtils.remove("corpPayType");
    }

    public static void clear() {
        SPStaticUtils.clear();
        SPUtils.getInstance().clear();
    }

}
