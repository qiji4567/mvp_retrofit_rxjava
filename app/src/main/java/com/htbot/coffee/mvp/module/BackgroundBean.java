package com.htbot.coffee.mvp.module;

public class BackgroundBean {
    private boolean isSelect;
    private int backGroundType;
    private int backGroundImage;

    public BackgroundBean(int backGroundType, int backGroundImage, boolean isSelect) {
        this.backGroundType = backGroundType;
        this.backGroundImage = backGroundImage;
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "BackgroundBean{" +
                "isSelect=" + isSelect +
                ", backGroundType=" + backGroundType +
                ", backGroundImage=" + backGroundImage +
                '}';
    }

    public int getBackGroundImage() {
        return backGroundImage;
    }

    public void setBackGroundImage(int backGroundImage) {
        this.backGroundImage = backGroundImage;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getBackGroundType() {
        return backGroundType;
    }

    public void setBackGroundType(int backGroundType) {
        this.backGroundType = backGroundType;
    }
}
