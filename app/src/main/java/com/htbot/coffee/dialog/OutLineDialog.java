package com.htbot.coffee.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.ClickUtils;
import com.htbot.coffee.R;
import com.htbot.coffee.ui.operation.LoginActivity;
import com.htbot.coffee.utils.ThemeUtils;


/**
 * 设备离线弹出框
 */
public class OutLineDialog extends Dialog {

    private final Context context;
    private ImageView ivMain;

    public OutLineDialog(Context context) {
        super(context);
        this.context = context;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void setBg() {
        ThemeUtils.setTheme(context, ivMain);
    }

    /**
     * 创建时
     *
     * @param savedInstanceState 保存实例状态
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_outline);

        // 获取窗口对象
        Window window = getWindow();
        if (window != null) {
            // 设置窗口大小为屏幕宽高
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            // 设置背景透明
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // 设置窗口重心为顶部
            window.getAttributes().gravity = Gravity.TOP;
        }

        initViews();
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        ImageView ivClose = findViewById(R.id.ivClose);
        ivMain = findViewById(R.id.ivMain);
        ThemeUtils.setTheme(context, ivMain);
        ClickUtils.expandClickArea(ivClose, 50);
        ivClose.setOnClickListener(v -> {
            Intent intent = new Intent(context, LoginActivity.class);
            getContext().startActivity(intent);
        });
    }

    @Override
    public void show() {
        if (isShowing()) return;
        super.show();

        // 在对话框显示时隐藏状态栏
        hideStatusBar();
    }

    private void hideStatusBar() {
        Window window = getWindow();
        if (window != null) {
            // 隐藏状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void dismiss() {
        if (!isShowing()) return;
        super.dismiss();

        // 关闭对话框时恢复状态栏
        restoreStatusBar();
    }

    private void restoreStatusBar() {
        Window window = getWindow();
        if (window != null) {
            // 恢复显示状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
