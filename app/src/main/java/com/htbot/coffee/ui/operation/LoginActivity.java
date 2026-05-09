package com.htbot.coffee.ui.operation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.htbot.coffee.R;
import com.htbot.coffee.base.BaseMvpActivity;
import com.htbot.coffee.databinding.ActivityLoginBinding;
import com.htbot.coffee.mvp.presenter.activity.LoginPresenter;
import com.htbot.coffee.mvp.presenter.contract.LoginContract;
import com.htbot.coffee.utils.KeyboardUtil;
import com.htbot.coffee.utils.ThemeUtils;

/**
 * 登录页
 *
 * 职责：
 * 1. 初始化 ViewBinding
 * 2. 初始化 UI
 * 3. 绑定事件
 * 4. 调用 Presenter
 * 5. 处理登录成功后的页面跳转
 * @author 53443
 */
public class LoginActivity extends BaseMvpActivity<ActivityLoginBinding, LoginPresenter>
        implements LoginContract.View {

    private static final String KEY_ADMIN_TOKEN = "adminToken";
    private static final String KEY_THEME_TYPE = "themeType";
    private static final int DEFAULT_THEME_TYPE = 0;

    @Override
    protected ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void setStatusBar() {
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                .init();
    }

    @Override
    protected void initialize() {
        initTheme();
        initListener();
    }

    private void initTheme() {
        int themeType = SPUtils.getInstance().getInt(KEY_THEME_TYPE, DEFAULT_THEME_TYPE);
        ThemeUtils.setThemeBg(binding.rlMain, themeType);
    }

    private void initListener() {
        binding.tvLogin.setOnClickListener(this);
        binding.tvBack.setOnClickListener(this);
        binding.ivMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == null || onViewClick()) {
            return;
        }

        int id = view.getId();

        if (id == R.id.tvLogin) {
            login();
        } else if (id == R.id.tvBack) {
            finish();
        } else if (id == R.id.ivMain) {
            hideKeyboard();
        }
    }

    private void login() {
        String account = binding.etName.getText() == null
                ? ""
                : binding.etName.getText().toString();

        String password = binding.etPwd.getText() == null
                ? ""
                : binding.etPwd.getText().toString();

        mPresenter.login(account, password);
    }

    @Override
    public void onUsernameEmpty() {
        showToast(getString(R.string.login_username_hint));
    }

    @Override
    public void onPasswordEmpty() {
        showToast(getString(R.string.login_password_hint));
    }

    @Override
    public void onLoginSuccess(@NonNull String token) {
        SPUtils.getInstance().put(KEY_ADMIN_TOKEN, token);
        startActivity(new Intent(this, OperationMainActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeUtils.setTheme(this, binding.ivMain);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            hideKeyboardWhenTouchOutsideCurrentFocus(event);
        }
        return super.dispatchTouchEvent(event);
    }

    private void hideKeyboardWhenTouchOutsideCurrentFocus(@NonNull MotionEvent event) {
        View currentFocus = getCurrentFocus();
        if (currentFocus == null || !isTouchOutsideView(currentFocus, event)) {
            return;
        }

        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    private void hideKeyboard() {
        KeyboardUtil.hideKeyboard(this);
    }

    private static boolean isTouchOutsideView(@Nullable View view, @NonNull MotionEvent event) {
        if (view == null) {
            return false;
        }

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];
        int right = left + view.getWidth();
        int bottom = top + view.getHeight();

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        return x < left || x > right || y < top || y > bottom;
    }
}