package com.htbot.coffee.mvp.module;

import com.google.gson.annotations.SerializedName;

public class MaterialNameBean {

    private String materialName;
    private Integer baseMaterialTypeId;
    private Integer baseMaterialId;
    @SerializedName("class")
    private String classX;
    private String materialTypeName;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getBaseMaterialTypeId() {
        return baseMaterialTypeId;
    }

    public void setBaseMaterialTypeId(Integer baseMaterialTypeId) {
        this.baseMaterialTypeId = baseMaterialTypeId;
    }

    public Integer getBaseMaterialId() {
        return baseMaterialId;
    }

    public void setBaseMaterialId(Integer baseMaterialId) {
        this.baseMaterialId = baseMaterialId;
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
