package com.htbot.coffee.mvp.module;

public class VersionItem {
    private int id;
    private String version;

    public VersionItem(int id, String version) {
        this.id = id;
        this.version = version;
    }

    // Getter和Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}