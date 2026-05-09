package com.htbot.coffee.mvp.module;

import androidx.annotation.NonNull;

public class UseExCouponBean {


    /**
     * data : {"recordId":2,"userId":"o2idt14wnjJbI77d1tg_5BqcVEwk","cmsContentId":10,"patternImg":"dolore"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * recordId : 2
         * userId : o2idt14wnjJbI77d1tg_5BqcVEwk
         * cmsContentId : 10
         * patternImg : dolore
         */

        private Integer recordId;
        private Integer goodId;
        private String type;
        private String patternImg;
        private Integer deviceId;

        public Integer getRecordId() {
            return recordId;
        }

        public void setRecordId(Integer recordId) {
            this.recordId = recordId;
        }

        public Integer getGoodId() {
            return goodId;
        }

        public void setGoodId(Integer goodId) {
            this.goodId = goodId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPatternImg() {
            return patternImg;
        }

        public void setPatternImg(String patternImg) {
            this.patternImg = patternImg;
        }

        public Integer getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Integer deviceId) {
            this.deviceId = deviceId;
        }

        @NonNull
        @Override
        public String toString() {
            return "DataBean{" +
                    "recordId=" + recordId +
                    ", goodId=" + goodId +
                    ", type='" + type + '\'' +
                    ", patternImg='" + patternImg + '\'' +
                    ", deviceId='" + deviceId + '\'' +
                    '}';
        }
    }
}
