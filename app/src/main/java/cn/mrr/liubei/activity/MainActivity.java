package cn.mrr.liubei.activity;

import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.mrr.liubei.R;
import cn.mrr.liubei.application.MyApplication;
import cn.mrr.liubei.base.BaseMVPActivity;
import cn.mrr.liubei.di.component.DaggerBaseActivityComponent;
import cn.mrr.liubei.module.BaseActivityModule;
import cn.mrr.liubei.mvp.presenter.activity.MainPresenter;
import cn.mrr.liubei.mvp.presenter.contract.BaseContractView;
import cn.mrr.liubei.utils.SystemUtil;
import cn.mvp.network.utils.LogUtils;


public class MainActivity extends BaseMVPActivity<MainPresenter> implements BaseContractView<Object> {

    private static final String TAG = "MainActivity";

    @BindView(R.id.editUsername)
    EditText editUsername;
    @BindView(R.id.editPassword)
    EditText editPassword;

    @Override
    public void initInject() {
        super.initInject();
        DaggerBaseActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .baseActivityModule(new BaseActivityModule(this))
                .build()
                .injectMainActivity(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        showReturn();
        setTitle("登录页");


    }

    private void showSystemParameter() {

        String TAG = "系统参数：";
        try {

            LogUtils.e(TAG, "手机厂商：" + SystemUtil.getDeviceBrand());
            LogUtils.e(TAG, "手机型号：" + SystemUtil.getSystemModel());
            LogUtils.e(TAG, "手机当前系统语言：" + SystemUtil.getSystemLanguage());
            LogUtils.e(TAG, "Android系统版本号：" + SystemUtil.getSystemVersion());
//            LogUtils.e(TAG, "手机IMEI：" + SystemUtil.getIMEI(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 点击事件
     */
    @OnClick(R.id.loginButton)
    public void onClick(View view) {
        if (super.onViewClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.loginButton:
                //          登录
                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                mPresenter.login(this,username, password);
                break;
            default:
                break;
        }

    }


    @Override
    public void updateUi(Object bean, int typeCode) {
        showMsg(bean.toString());
    }
}