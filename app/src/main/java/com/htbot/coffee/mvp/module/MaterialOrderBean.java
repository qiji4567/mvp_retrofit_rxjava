package com.htbot.coffee.mvp.module;

import com.google.gson.annotations.SerializedName;

public class MaterialOrderBean {

    private String code;
    private Integer baseMaterialId;
    private double remain;
    private Long updateTime;
    private Integer deviceId;
    private Integer capacity;
    private Integer stopThreshold;
    private String materialName;
    private String unit;
    private Integer createBy;
    private Integer countWay;
    private Long createTime;
    private Integer updateBy;
    private Integer alertThreshold;
    private String name;
    private Integer id;
    @SerializedName("class")
    private String classX;
    private Integer isDel;
    private String tare;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTare() {
        return tare;
    }

    public void setTare(String tare) {
        this.tare = tare;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getBaseMaterialId() {
        return baseMaterialId;
    }

    public void setBaseMaterialId(Integer baseMaterialId) {
        this.baseMaterialId = baseMaterialId;
    }

    public double getRemain() {
        return remain;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getStopThreshold() {
        return stopThreshold;
    }

    public void setStopThreshold(Integer stopThreshold) {
        this.stopThreshold = stopThreshold;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getCountWay() {
        return countWay;
    }

    public void setCountWay(Integer countWay) {
        this.countWay = countWay;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getAlertThreshold() {
        return alertThreshold;
    }

    public void setAlertThreshold(Integer alertThreshold) {
        this.alertThreshold = alertThreshold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
