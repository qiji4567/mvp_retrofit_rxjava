package com.htbot.coffee.mvp.module;

public class AddBeansBean {
    private double count;
    private String unit;
    private boolean isClick;

    public int getCount() {
        return (int) count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
