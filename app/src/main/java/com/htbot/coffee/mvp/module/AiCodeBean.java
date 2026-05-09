package com.htbot.coffee.mvp.module;

public class AiCodeBean {
    private String wxQrCode;
    private String makeOrderId;
    private String wxQrUrl;
    private String mergeUrl;
    private Boolean queryUrl;

    public String getMakeOrderId() {
        return makeOrderId;
    }

    public void setMakeOrderId(String makeOrderId) {
        this.makeOrderId = makeOrderId;
    }

    public String getWxQrCode() {
        return wxQrCode;
    }

    public void setWxQrCode(String wxQrCode) {
        this.wxQrCode = wxQrCode;
    }

    public String getWxQrUrl() {
        return wxQrUrl;
    }

    public void setWxQrUrl(String wxQrUrl) {
        this.wxQrUrl = wxQrUrl;
    }

    public String getMergeUrl() {
        return mergeUrl;
    }

    public void setMergeUrl(String mergeUrl) {
        this.mergeUrl = mergeUrl;
    }

    public Boolean getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(Boolean queryUrl) {
        this.queryUrl = queryUrl;
    }
}
