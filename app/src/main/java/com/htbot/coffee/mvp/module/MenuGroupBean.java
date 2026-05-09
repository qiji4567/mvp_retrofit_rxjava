package com.htbot.coffee.mvp.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuGroupBean {
    private boolean isClick;
    private int menuType;//菜单风格

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    private int currentIndex;
    private Long updateTime;
    private Integer sort;
    private Integer deviceId;
    private String groupImages;
    private Integer updateBy;
    private List<DeviceGoodSBean> deviceGoodS;
    private String name;
    private Integer id;
    @SerializedName("class")
    private String classX;
    private Integer isDel;
    private Integer status;
    /**
     * 0 普通菜单；1 花式印花菜单；2 AI印花菜单
     */
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
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

    public String getGroupImages() {
        return groupImages;
    }

    public void setGroupImages(String groupImages) {
        this.groupImages = groupImages;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public List<DeviceGoodSBean> getDeviceGoodS() {
        return deviceGoodS;
    }

    public void setDeviceGoodS(List<DeviceGoodSBean> deviceGoodS) {
        this.deviceGoodS = deviceGoodS;
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

    public static class DeviceGoodSBean {
        private Double originalPrice;
        private String coverImg;
        private Integer deviceId;
        private Double price;
        private Integer id;
        @SerializedName("class")
        private String classX;
        private String smallImg;
        private String iconImg;
        private String animationImg;
        private Integer baseGoodId;
        private Integer sort;
        private Integer createBy;
        private Long createTime;
        private String name;
        private Integer isDel;
        private Integer status;
        private Integer salesStatus;
        private boolean isSelect;
        private int r;
        private int g;
        private int b;
        private int alpha;
        private String productImg;

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public Integer getSalesStatus() {
            return salesStatus;
        }

        public void setSalesStatus(Integer salesStatus) {
            this.salesStatus = salesStatus;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public Double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(Double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public Integer getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Integer deviceId) {
            this.deviceId = deviceId;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
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

        public String getSmallImg() {
            return smallImg;
        }

        public void setSmallImg(String smallImg) {
            this.smallImg = smallImg;
        }

        public String getIconImg() {
            return iconImg;
        }

        public void setIconImg(String iconImg) {
            this.iconImg = iconImg;
        }

        public String getAnimationImg() {
            return animationImg;
        }

        public void setAnimationImg(String animationImg) {
            this.animationImg = animationImg;
        }

        public Integer getBaseGoodId() {
            return baseGoodId;
        }

        public void setBaseGoodId(Integer baseGoodId) {
            this.baseGoodId = baseGoodId;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
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
    }
}
