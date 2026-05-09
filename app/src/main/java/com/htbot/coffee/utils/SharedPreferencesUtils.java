package com.htbot.coffee.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.htbot.coffee.MyApplication;

/**
 * SharedPreferences 工具类
 */
public class SharedPreferencesUtils {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String PREF_NAME = "my_preferences"; // 你可以自定义文件名

    // 初始化 SharedPreferences
    private static void init() {
        if (sharedPreferences == null) {
            sharedPreferences = MyApplication.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    // 保存 String 类型数据
    public static void putString(String key, String value) {
        init();
        editor.putString(key, value);
        editor.apply();
    }

    // 获取 String 类型数据
    public static String getString(String key, String defaultValue) {
        init();
        return sharedPreferences.getString(key, defaultValue);
    }

    // 保存 int 类型数据
    public static void putInt(String key, int value) {
        init();
        editor.putInt(key, value);
        editor.apply();
    }

    // 获取 int 类型数据
    public static int getInt(String key, int defaultValue) {
        init();
        return sharedPreferences.getInt(key, defaultValue);
    }

    // 保存 boolean 类型数据
    public static void putBoolean(String key, boolean value) {
        init();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // 获取 boolean 类型数据
    public static boolean getBoolean(String key, boolean defaultValue) {
        init();
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // 保存 long 类型数据
    public static void putLong(String key, long value) {
        init();
        editor.putLong(key, value);
        editor.apply();
    }

    // 获取 long 类型数据
    public static long getLong(String key, long defaultValue) {
        init();
        return sharedPreferences.getLong(key, defaultValue);
    }

    // 删除某个键值对
    public static void remove(String key) {
        init();
        editor.remove(key);
        editor.apply();
    }

    // 清除所有数据
    public static void clear() {
        init();
        editor.clear();
        editor.apply();
    }

    // 判断是否存在某个 key
    public static boolean contains(String key) {
        init();
        return sharedPreferences.contains(key);
    }
}
