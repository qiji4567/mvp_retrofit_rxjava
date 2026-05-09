package com.htbot.coffee.mvp.module;

public class MyEvent {
    private String message;
    private int type;

    public MyEvent(String message) {
        this.message = message;
    }

    public MyEvent(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }
}
