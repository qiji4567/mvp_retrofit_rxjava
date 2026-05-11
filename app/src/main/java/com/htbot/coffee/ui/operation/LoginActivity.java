package com.htbot.coffee.ui.operation;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.htbot.coffee.R;
import com.htbot.coffee.base.BaseMvpActivity;
import com.htbot.coffee.databinding.ActivityLoginBinding;
import com.htbot.coffee.mvp.presenter.activity.LoginPresenter;
import com.htbot.coffee.mvp.presenter.contract.BaseContractView;
import com.htbot.coffee.utils.KeyboardUtil;
import com.htbot.coffee.utils.ThemeUtils;

/**
 * 登录
 * @author 53443
 */
public class LoginActivity extends BaseMvpActivity<ActivityLoginBinding, LoginPresenter>
        implements BaseContractView<String> {

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
        int themeType = SPUtils.getInstance().getInt("themeType", 0);
        ThemeUtils.setThemeBg(binding.rlMain, themeType);
    }

    private void initListener() {
        binding.tvLogin.setOnClickListener(this);
        binding.tvBack.setOnClickListener(this);
        binding.ivMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onViewClick()) {
            return;
        }

        int id = v.getId();

        if (id == R.id.tvLogin) {
            login();
        } else if (id == R.id.tvBack) {
            finish();
        } else if (id == R.id.ivMain) {
            KeyboardUtil.hideKeyboard(this);
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
    public void updateUi(String token, int typeCode) {
        if (typeCode == LoginPresenter.TYPE_LOGIN_SUCCESS) {
            SPUtils.getInstance().put("adminToken", token);
            startActivity(new Intent(this, OperationMainActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeUtils.setTheme(this, binding.ivMain);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev != null && ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();

            if (view != null && isTouchOutsideView(view, ev)) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    private boolean isTouchOutsideView(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        return x < location[0]
                || x > location[0] + view.getWidth()
                || y < location[1]
                || y > location[1] + view.getHeight();
    }
}