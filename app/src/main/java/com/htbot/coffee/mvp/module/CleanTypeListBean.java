package com.htbot.coffee.mvp.module;

import com.google.gson.annotations.SerializedName;

public class CleanTypeListBean {


    /**
     * cleanTypeDesc : 废水处理
     * cleanType : WASTE_WATER
     * overdue : false
     * lastWashTime : 1776831071000
     * class : com.htyjyfb.bot.good.api.vo.DeviceCleaningTypeVO
     */

    private String cleanTypeDesc;
    private String cleanType;
    private Boolean overdue;
    private Long lastWashTime;
    @SerializedName("class")
    private String classX;

    public String getCleanTypeDesc() {
        return cleanTypeDesc;
    }

    public void setCleanTypeDesc(String cleanTypeDesc) {
        this.cleanTypeDesc = cleanTypeDesc;
    }

    public String getCleanType() {
        return cleanType;
    }

    public void setCleanType(String cleanType) {
        this.cleanType = cleanType;
    }

    public Boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    public Long getLastWashTime() {
        return lastWashTime;
    }

    public void setLastWashTime(Long lastWashTime) {
        this.lastWashTime = lastWashTime;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }
}
