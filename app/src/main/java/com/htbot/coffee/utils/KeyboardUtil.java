package com.htbot.coffee.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.htbot.coffee.base.BaseActivity;

/**
 * 软键盘工具类，用于隐藏或显示键盘。
 */
public class KeyboardUtil {

    /**
     * 隐藏软键盘。
     *
     * @param activity 当前活动的 Activity
     */
    public static void hideKeyboard(BaseActivity activity) {
        // 1. 获取输入法服务
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        // 2. 获取当前获得焦点的视图
        View view = activity.getCurrentFocus();
        if (view != null) {
            // 3. 从视图的窗口中隐藏软键盘
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
