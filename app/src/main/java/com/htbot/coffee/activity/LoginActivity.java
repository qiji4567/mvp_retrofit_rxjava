package com.htbot.coffee.activity;

import android.view.View;

import com.htbot.coffee.R;
import com.htbot.coffee.base.BaseMVPActivity;
import com.htbot.coffee.databinding.ActivityLoginBinding;
import com.htbot.coffee.mvp.presenter.activity.LoginPresenter;
import com.htbot.coffee.mvp.presenter.contract.BaseContractView;
import com.htbot.coffee.utils.LogUtils;
import com.htbot.coffee.utils.SystemUtil;

/**
 * @author 53443
 */
public class LoginActivity extends BaseMVPActivity<ActivityLoginBinding, LoginPresenter>
        implements BaseContractView<Object> {

    @Override
    protected ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }


    @Override
    protected void initialize() {
        binding = ActivityLoginBinding.bind(getContentView());
        showReturn();
        setTitle("登录页");
        binding.editUsername.setText("13121146221");
        binding.editPassword.setText("888888");
        binding.loginButton.setOnClickListener(this::onClick);
    }

    private void showSystemParameter() {
        String tag = "系统参数：";
        try {
            LogUtils.e(tag, "手机厂商：" + SystemUtil.getDeviceBrand());
            LogUtils.e(tag, "手机型号：" + SystemUtil.getSystemModel());
            LogUtils.e(tag, "手机当前系统语言：" + SystemUtil.getSystemLanguage());
            LogUtils.e(tag, "Android系统版本号：" + SystemUtil.getSystemVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        if (super.onViewClick()) return;
        if (view.getId() == R.id.loginButton) {
            String username = binding.editUsername.getText().toString().trim();
            String password = binding.editPassword.getText().toString().trim();
            mPresenter.login(username, password);
        }
    }

    @Override
    public void updateUi(Object bean, int typeCode) {
        showMsg(bean.toString());
        startActivity(this, MainActivity.class, false);
    }


}
