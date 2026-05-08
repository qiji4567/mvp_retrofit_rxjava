package cn.mrr.liubei.activity;

import android.view.View;

import cn.mrr.liubei.R;
import cn.mrr.liubei.net.RetrofitHelper;
import cn.mrr.liubei.base.BaseMVPActivity;
import cn.mrr.liubei.databinding.ActivityMainBinding;
import cn.mrr.liubei.mvp.presenter.activity.MainPresenter;
import cn.mrr.liubei.mvp.presenter.contract.BaseContractView;

public class MainActivity extends BaseMVPActivity<MainPresenter> implements BaseContractView<Object> {
    private ActivityMainBinding binding;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(RetrofitHelper.getApiService(), this);
    }

    @Override protected int getLayoutId() { return R.layout.activity_main; }

    @Override protected void initialize() {
        binding = ActivityMainBinding.bind(getContentView());
        showReturn();
        setTitle("首页");
        binding.tvImageview.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        if (super.onViewClick()) {
            return;
        }
        if (view.getId() == R.id.tv_imageview) {
            startActivity(mContext, ActionBarActivity.class, false);
        }
    }

    @Override public void updateUi(Object bean, int typeCode) {}
}
