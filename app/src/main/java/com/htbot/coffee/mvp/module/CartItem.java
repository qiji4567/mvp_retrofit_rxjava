package com.htbot.coffee.mvp.module;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private int goodsId;                // 商品ID
    private int baseGoodId;            // 基础商品ID
    private String name;               // 商品名称
    private double price;              // 单价
    private double originalPrice;      // 原价
    private String smallImg;           // 小图
    private String coverImg;           // 封面图
    private String materialNames;      // 材料名称（区分同商品不同配置）
    private int quantity = 1;          // 数量
    private long createTime;           // 加入时间
    private String goodCode;           // 商品编码
    private int pageType;              // 页面类型
    private String groupCode;          // 分组编码

    private boolean isSale;//是否告罄

    private boolean isCheck;
    private String flower;
    private Integer blindBoxImage;
    private boolean isBlindBox;

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

    public void setFlower(String flower) {
        this.flower = flower;
    }

    public String getFlower() {
        return flower;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isSale() {
        return isSale;
    }

    public void setSale(boolean sale) {
        isSale = sale;
    }

    // 计算总价
    public double getTotalPrice() {
        return price * quantity;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getBaseGoodId() {
        return baseGoodId;
    }

    public void setBaseGoodId(int baseGoodId) {
        this.baseGoodId = baseGoodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
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

    public String getMaterialNames() {
        return materialNames;
    }

    public void setMaterialNames(String materialNames) {
        this.materialNames = materialNames;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public int getPageType() {
        return pageType;
    }

    public void setPageType(int pageType) {
        this.pageType = pageType;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}