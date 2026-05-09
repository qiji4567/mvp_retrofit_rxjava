package cn.mrr.liubei.activity;

import android.view.View;

import cn.mrr.liubei.R;
import cn.mrr.liubei.base.BaseMVPActivity;
import cn.mrr.liubei.databinding.ActivityMainBinding;
import cn.mrr.liubei.mvp.presenter.activity.MainPresenter;
import cn.mrr.liubei.mvp.presenter.contract.BaseContractView;

/**
 * @author 53443
 */
public class MainActivity extends BaseMVPActivity<ActivityMainBinding,MainPresenter> implements BaseContractView<Object> {

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter( this);
    }


    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initialize() {
        showReturn();
        setTitle("首页");
        binding.tvImageview.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        if (super.onViewClick()) return;
        if (view.getId() == R.id.tv_imageview)
            startActivity(mContext, ActionBarActivity.class, false);
    }

    @Override
    public void updateUi(Object bean, int typeCode) {
    }
}
