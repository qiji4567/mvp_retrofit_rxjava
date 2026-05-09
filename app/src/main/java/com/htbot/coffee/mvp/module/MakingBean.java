package com.htbot.coffee.mvp.module;

import com.google.gson.annotations.SerializedName;

public class MakingBean {

    private String orderSubId;
    private String itemId;
    private String makeType;
    private String itemName;
    private Integer process;
    private String smallImg;
    private String orderId;
    private String productImg;
    private String makeStatus;
    @SerializedName("class")
    private String classX;
    private String makeStatusName;
    private String flower;
    private String orderWaitId;
    private String orderWaitNum;

    public String getOrderSubId() {
        return orderSubId;
    }

    public void setOrderSubId(String orderSubId) {
        this.orderSubId = orderSubId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getMakeType() {
        return makeType;
    }

    public void setMakeType(String makeType) {
        this.makeType = makeType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getMakeStatus() {
        return makeStatus;
    }

    public void setMakeStatus(String makeStatus) {
        this.makeStatus = makeStatus;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getMakeStatusName() {
        return makeStatusName;
    }

    public void setMakeStatusName(String makeStatusName) {
        this.makeStatusName = makeStatusName;
    }

    public String getFlower() {
        return flower;
    }

    public void setFlower(String flower) {
        this.flower = flower;
    }

    public String getOrderWaitId() {
        return orderWaitId;
    }

    public void setOrderWaitId(String orderWaitId) {
        this.orderWaitId = orderWaitId;
    }

    public String getOrderWaitNum() {
        return orderWaitNum;
    }

    public void setOrderWaitNum(String orderWaitNum) {
        this.orderWaitNum = orderWaitNum;
    }
}
