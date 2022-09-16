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

    @Override
    public void initInject() {
        super.initInject();
        DaggerBaseActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .baseActivityModule(new BaseActivityModule(this))
                .build()
                .injectActivity(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        showReturn();
        setTitle("首页");


    }


    /**
     * 点击事件
     */
    @OnClick(R.id.tv_imageview)
    public void onClick(View view) {
        if (super.onViewClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_imageview:
                //          登录
                startActivity(mContext, ActionBarActivity.class, false);
                break;
            default:
                break;
        }

    }


    @Override
    public void updateUi(Object bean, int typeCode) {
    }
}