package com.htbot.coffee.mvp.module;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GoodsBean implements Serializable {

    private Integer taskMode;
    private Double originalPrice;
    private String smallImg;
    private String coverImg;
    private String iconImg;
    private String animationImg;
    private Integer baseGoodId;
    private Integer sort;
    private Integer appCode;
    private String groupName;
    private Double price;
    private String name;
    private Integer id;
    @SerializedName("class")
    private String classX;
    private String goodCode;
    private List<MaterialBinVOListBean> materialBinVOList;
    private Integer status;
    private String groupCode;
    private String materialNames;
    private int pageType = 0;//0正常页面 1、花式印花 2、ai印花
    private String flower;
    private String productImg;

    private Integer blindBoxImage;
    private boolean isBlindBox;

    // 合并数据的方法：以 DeviceGoodSBean 数据为准，若为空则使用 GoodsBean 数据
    public static GoodsBean mergeDeviceGoodSBeanAndGoodsBean(MenuGroupBean.DeviceGoodSBean deviceGoodSBean, GoodsBean goodsBean) {
        GoodsBean mergedGoodsBean = new GoodsBean();

        // 优先使用 DeviceGoodSBean 数据，如果为 null 则使用 GoodsBean 数据
        mergedGoodsBean.setOriginalPrice(deviceGoodSBean.getOriginalPrice() != null ? deviceGoodSBean.getOriginalPrice() : goodsBean.getOriginalPrice());
        mergedGoodsBean.setCoverImg(deviceGoodSBean.getCoverImg() != null ? deviceGoodSBean.getCoverImg() : goodsBean.getCoverImg());
        mergedGoodsBean.setPrice(deviceGoodSBean.getPrice() != null ? deviceGoodSBean.getPrice() : goodsBean.getPrice());
        mergedGoodsBean.setId(deviceGoodSBean.getId() != null ? deviceGoodSBean.getId() : goodsBean.getId());
        mergedGoodsBean.setClassX(deviceGoodSBean.getClassX() != null ? deviceGoodSBean.getClassX() : goodsBean.getClassX());
        mergedGoodsBean.setSmallImg(deviceGoodSBean.getSmallImg() != null ? deviceGoodSBean.getSmallImg() : goodsBean.getSmallImg());
        mergedGoodsBean.setIconImg(deviceGoodSBean.getIconImg() != null ? deviceGoodSBean.getIconImg() : goodsBean.getIconImg());
        mergedGoodsBean.setAnimationImg(deviceGoodSBean.getAnimationImg() != null ? deviceGoodSBean.getAnimationImg() : goodsBean.getAnimationImg());
        mergedGoodsBean.setBaseGoodId(deviceGoodSBean.getBaseGoodId() != null ? deviceGoodSBean.getBaseGoodId() : goodsBean.getBaseGoodId());
        mergedGoodsBean.setSort(deviceGoodSBean.getSort() != null ? deviceGoodSBean.getSort() : goodsBean.getSort());
        mergedGoodsBean.setName(deviceGoodSBean.getName() != null ? deviceGoodSBean.getName() : goodsBean.getName());
        mergedGoodsBean.setProductImg(deviceGoodSBean.getProductImg() != null ? deviceGoodSBean.getProductImg() : goodsBean.getProductImg());

        // 如果 DeviceGoodSBean 中的 status 不为空，则使用该值，否则使用 GoodsBean 中的值
        mergedGoodsBean.setStatus(deviceGoodSBean.getStatus() != null ? deviceGoodSBean.getStatus() : goodsBean.getStatus());

        // 设置 pageType 的默认值为 0，如果有特殊需求可以修改
        mergedGoodsBean.setPageType(goodsBean.getPageType());

        // 对于一些字段，直接使用 goodsBean 中的值
        mergedGoodsBean.setTaskMode(goodsBean.getTaskMode());
        mergedGoodsBean.setAppCode(goodsBean.getAppCode());
        mergedGoodsBean.setGroupName(goodsBean.getGroupName());
        mergedGoodsBean.setGoodCode(goodsBean.getGoodCode());

        // 对于 materialBinVOList，直接使用 goodsBean 中的值
        mergedGoodsBean.setMaterialBinVOList(goodsBean.getMaterialBinVOList());

        // 如果 flower 不为空，使用 DeviceGoodSBean 的值，否则使用 GoodsBean 的值
        mergedGoodsBean.setFlower(goodsBean.getFlower());
        mergedGoodsBean.setBlindBox(goodsBean.isBlindBox());
        mergedGoodsBean.setBlindBoxImage(goodsBean.getBlindBoxImage());

        return mergedGoodsBean;
    }

    public Integer getBlindBoxImage() {
        return blindBoxImage;
    }

    public void setBlindBoxImage(Integer blindBoxImage) {
        this.blindBoxImage = blindBoxImage;
    }

    public boolean isBlindBox() {
        return isBlindBox;
    }

    public void setBlindBox(boolean blindBox) {
        isBlindBox = blindBox;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getFlower() {
        return flower;
    }

    public void setFlower(String flower) {
        this.flower = flower;
    }

    public int getPageType() {
        return pageType;
    }

    public void setPageType(int pageType) {
        this.pageType = pageType;
    }

    private int goodCount;

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public String getMaterialNames() {
        return materialNames;
    }

    public void setMaterialNames(String materialNames) {
        this.materialNames = materialNames;
    }

    public Integer getTaskMode() {
        return taskMode;
    }

    public void setTaskMode(Integer taskMode) {
        this.taskMode = taskMode;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
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

    public Integer getAppCode() {
        return appCode;
    }

    public void setAppCode(Integer appCode) {
        this.appCode = appCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public List<MaterialBinVOListBean> getMaterialBinVOList() {
        return materialBinVOList;
    }

    public void setMaterialBinVOList(List<MaterialBinVOListBean> materialBinVOList) {
        this.materialBinVOList = materialBinVOList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public static class MaterialBinVOListBean implements Serializable {
        private String materialTypeCode;
        private Integer baseMaterialTypeId;
        private Integer quantity;
        private Integer baseMaterialId;
        private Integer baseGoodId;
        private String materialCode;
        private String materialName;
        private String unit;
        private Integer createBy;
        private Long createTime;
        private String deviceMaterialBinCode;
        private Integer id;
        private String processMode;
        @SerializedName("class")
        private String classX;
        private String materialTypeName;

        public String getMaterialTypeCode() {
            return materialTypeCode;
        }

        public void setMaterialTypeCode(String materialTypeCode) {
            this.materialTypeCode = materialTypeCode;
        }

        public Integer getBaseMaterialTypeId() {
            return baseMaterialTypeId;
        }

        public void setBaseMaterialTypeId(Integer baseMaterialTypeId) {
            this.baseMaterialTypeId = baseMaterialTypeId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getBaseMaterialId() {
            return baseMaterialId;
        }

        public void setBaseMaterialId(Integer baseMaterialId) {
            this.baseMaterialId = baseMaterialId;
        }

        public Integer getBaseGoodId() {
            return baseGoodId;
        }

        public void setBaseGoodId(Integer baseGoodId) {
            this.baseGoodId = baseGoodId;
        }

        public String getMaterialCode() {
            return materialCode;
        }

        public void setMaterialCode(String materialCode) {
            this.materialCode = materialCode;
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

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public String getDeviceMaterialBinCode() {
            return deviceMaterialBinCode;
        }

        public void setDeviceMaterialBinCode(String deviceMaterialBinCode) {
            this.deviceMaterialBinCode = deviceMaterialBinCode;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getProcessMode() {
            return processMode;
        }

        public void setProcessMode(String processMode) {
            this.processMode = processMode;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

        public String getMaterialTypeName() {
            return materialTypeName;
        }

        public void setMaterialTypeName(String materialTypeName) {
            this.materialTypeName = materialTypeName;
        }
    }
}
