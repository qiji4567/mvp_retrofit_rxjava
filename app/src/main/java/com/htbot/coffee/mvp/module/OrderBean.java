package com.htbot.coffee.mvp.module;

import com.google.gson.annotations.SerializedName;

public class OrderBean {

    private String qrCode;
    private String orderId;
    private String orderWaitId;
    private String orderParam;

    public String getOrderWaitId() {
        return orderWaitId;
    }

    public void setOrderWaitId(String orderWaitId) {
        this.orderWaitId = orderWaitId;
    }

    @SerializedName("class")
    private String classX;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderParam() {
        return orderParam;
    }

    public void setOrderParam(String orderParam) {
        this.orderParam = orderParam;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }
}
