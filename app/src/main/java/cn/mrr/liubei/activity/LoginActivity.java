package cn.mrr.liubei.activity;

import android.view.View;

import cn.mrr.liubei.R;
import cn.mrr.liubei.base.BaseMVPActivity;
import cn.mrr.liubei.databinding.ActivityLoginBinding;
import cn.mrr.liubei.mvp.presenter.activity.LoginPresenter;
import cn.mrr.liubei.mvp.presenter.contract.BaseContractView;
import cn.mrr.liubei.utils.SystemUtil;
import cn.mvp.network.utils.LogUtils;

public class LoginActivity extends BaseMVPActivity<LoginPresenter> implements BaseContractView<Object> {
    private ActivityLoginBinding binding;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(RetrofitHelper.getApiService(), this);
    }

    @Override protected int getLayoutId() { return R.layout.activity_login; }

    @Override protected void initialize() {
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
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void onClick(View view) {
        if (super.onViewClick()) return;
        if (view.getId() == R.id.loginButton) {
            String username = binding.editUsername.getText().toString().trim();
            String password = binding.editPassword.getText().toString().trim();
            mPresenter.login(username, password);
        }
    }

    @Override public void updateUi(Object bean, int typeCode) {
        showMsg(bean.toString());
        startActivity(this, MainActivity.class, false);
    }
}
