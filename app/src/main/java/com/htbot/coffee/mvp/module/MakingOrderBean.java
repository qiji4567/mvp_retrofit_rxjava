package com.htbot.coffee.mvp.module;

public class MakingOrderBean {
    private String orderSubId;
    private String itemId;
    private String makeType;
    private String itemName;
    private String smallImg;
    private String orderId;
    private String productImg;
    private String makeStatus;
    private String className;
    private String makeStatusName;
    private String flower;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderSubId() {
        return orderSubId;
    }

    public void setOrderSubId(String orderSubId) {
        this.orderSubId = orderSubId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFlower() {
        return flower;
    }

    public void setFlower(String flower) {
        this.flower = flower;
    }

    public String getMakeType() {
        return makeType;
    }

    public void setMakeType(String makeType) {
        this.makeType = makeType;
    }

    public String getMakeStatus() {
        return makeStatus;
    }

    public void setMakeStatus(String makeStatus) {
        this.makeStatus = makeStatus;
    }

    public String getMakeStatusName() {
        return makeStatusName;
    }

    public void setMakeStatusName(String makeStatusName) {
        this.makeStatusName = makeStatusName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }


}
