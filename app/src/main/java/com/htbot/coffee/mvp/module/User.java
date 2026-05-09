package com.htbot.coffee.mvp.module;

/**
 * 用户信息
 */
public class User {
    private String userName;   // 用户名
    private String passWord;   // 密码
    private String userPhone;  // 用户手机号码
    private String nickname;   // 用户昵称
    private int age;           // 用户年龄
    private String description; // 用户描述

    // 构造函数，用于初始化用户信息
    public User(String userName, String userPhone, String passWord, String nickname, int age, String description) {
        this.userName = userName;      // 初始化用户名
        this.userPhone = userPhone;    // 初始化用户手机号码
        this.passWord = passWord;      // 初始化密码
        this.nickname = nickname;      // 初始化用户昵称
        this.age = age;                // 初始化用户年龄
        this.description = description; // 初始化用户描述
    }

    // Getter 和 Setter 方法，用于获取和设置用户信息

    public String getUserName() {
        return userName;  // 获取用户名
    }

    public void setUserName(String userName) {
        this.userName = userName;  // 设置用户名
    }

    public String getPassWord() {
        return passWord;  // 获取密码
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;  // 设置密码
    }

    public String getUserPhone() {
        return userPhone;  // 获取用户手机号码
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;  // 设置用户手机号码
    }

    public String getNickname() {
        return nickname;  // 获取用户昵称
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;  // 设置用户昵称
    }

    public int getAge() {
        return age;  // 获取用户年龄
    }

    public void setAge(int age) {
        this.age = age;  // 设置用户年龄
    }

    public String getDescription() {
        return description;  // 获取用户描述
    }

    public void setDescription(String description) {
        this.description = description;  // 设置用户描述
    }
}
