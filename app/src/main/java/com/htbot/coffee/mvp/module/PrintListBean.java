package com.htbot.coffee.mvp.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrintListBean {

    private List<PatternSBean> patternS;
    private Integer sort;
    private Integer deviceId;
    private Integer createBy;
    private Long createTime;
    private String name;
    private Integer id;
    @SerializedName("class")
    private String classX;
    private Integer isDel;
    private Integer status;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public List<PatternSBean> getPatternS() {
        return patternS;
    }

    public void setPatternS(List<PatternSBean> patternS) {
        this.patternS = patternS;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static class PatternSBean {
        private Integer devicePrintId;
        private Integer createBy;
        private Long createTime;
        private String name;
        private Integer id;
        private Integer sort;
        @SerializedName("class")
        private String classX;
        private String patternImg;
        private Integer status;
        private boolean isSelect;
        private Integer blindBox;
        private boolean isBlindBox;

        public boolean isBlindBox() {
            return isBlindBox;
        }

        public void setBlindBox(boolean blindBox) {
            isBlindBox = blindBox;
        }

        public Integer getBlindBox() {
            return blindBox;
        }

        public void setBlindBox(Integer blindBox) {
            this.blindBox = blindBox;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public Integer getDevicePrintId() {
            return devicePrintId;
        }

        public void setDevicePrintId(Integer devicePrintId) {
            this.devicePrintId = devicePrintId;
        }

        public Integer getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Integer createBy) {
            this.createBy = createBy;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
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

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

        public String getPatternImg() {
            return patternImg;
        }

        public void setPatternImg(String patternImg) {
            this.patternImg = patternImg;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
}
