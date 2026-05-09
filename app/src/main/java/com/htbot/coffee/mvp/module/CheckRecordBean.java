package com.htbot.coffee.mvp.module;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 每日清理签到记录
 */
public class CheckRecordBean implements Serializable {


    /**
     * total : 2
     * code : SUCCESS
     * data : [{"createdAt":1776842400000,"serialNumber":"68415caad582f200182e4151_hcbot-test-device","cleanType":"WASTE_WATER","loginInfo":null,"remark":null,"washTime":1776831071000,"id":3,"class":"com.htyjyfb.bot.good.api.dto.DeviceCleaningRecordDTO","deviceId":2,"operatorId":1,"operatorName":"张三"},{"createdAt":1776842410000,"serialNumber":"68415caad582f200182e4151_hcbot-test-device","cleanType":"WASTE_WATER","loginInfo":null,"remark":null,"washTime":1776831071000,"id":4,"class":"com.htyjyfb.bot.good.api.dto.DeviceCleaningRecordDTO","deviceId":2,"operatorId":1,"operatorName":"张三"}]
     * pageIndex : 1
     * success : true
     * pageSize : 10
     * message : 执行成功
     * class : com.htyjyfb.bot.base.dto.PageDTO
     */

    private Integer total;
    private String code;
    private Integer pageIndex;
    private Boolean success;
    private Integer pageSize;
    private String message;

    private List<DataBean> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createdAt : 1776842400000
         * serialNumber : 68415caad582f200182e4151_hcbot-test-device
         * cleanType : WASTE_WATER
         * loginInfo : null
         * remark : null
         * washTime : 1776831071000
         * id : 3
         * class : com.htyjyfb.bot.good.api.dto.DeviceCleaningRecordDTO
         * deviceId : 2
         * operatorId : 1
         * operatorName : 张三
         */

        private Long createdAt;
        private String serialNumber;
        private String cleanType;
        private Object loginInfo;
        private Object remark;
        private Long washTime;
        private Integer id;
        @SerializedName("class")
        private String classX;
        private Integer deviceId;
        private Integer operatorId;
        private String operatorName;

        public Long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Long createdAt) {
            this.createdAt = createdAt;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getCleanType() {
            return cleanType;
        }

        public void setCleanType(String cleanType) {
            this.cleanType = cleanType;
        }

        public Object getLoginInfo() {
            return loginInfo;
        }

        public void setLoginInfo(Object loginInfo) {
            this.loginInfo = loginInfo;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Long getWashTime() {
            return washTime;
        }

        public void setWashTime(Long washTime) {
            this.washTime = washTime;
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

        public Integer getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Integer deviceId) {
            this.deviceId = deviceId;
        }

        public Integer getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
        }

        public String getOperatorName() {
            return operatorName;
        }

        public void setOperatorName(String operatorName) {
            this.operatorName = operatorName;
        }
    }
}