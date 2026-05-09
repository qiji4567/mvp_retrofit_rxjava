package com.htbot.coffee.utils;


import android.content.Context;


public class LanguageType {
    public static final int LANGUAGE_FOLLOW_SYSTEM = 0; //跟随系统
    public static final int LANGUAGE_CHINESE_SIMPLIFIED = 0; //简体
    public static final int LANGUAGE_EN = 1;    //英文
    public static final int LANGUAGE_KOREAN = 2;  //韩语

    public static boolean isCN(Context context) {
        return context.getResources().getConfiguration().locale.getCountry().equals("CN");
    }
}
