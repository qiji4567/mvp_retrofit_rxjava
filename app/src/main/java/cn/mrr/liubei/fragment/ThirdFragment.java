package cn.mrr.liubei.fragment;

import androidx.annotation.ColorInt;

import cn.mrr.liubei.R;
import cn.mrr.liubei.base.BaseFragment;
import cn.mrr.liubei.base.BaseMVPFragment;
import cn.mrr.liubei.databinding.Layout3Binding;

public class ThirdFragment extends BaseFragment {
    private Layout3Binding binding;

    @Override
    protected int getLayoutId() {
        return R.layout.layout3;
    }

    @Override
    protected void initialize() {
        binding = Layout3Binding.bind(mView);
    }

    public void setTvTitleBackgroundColor(@ColorInt int color) {
        if (binding != null) {
            binding.tvTitle.setBackgroundColor(color);
            binding.fakeStatusBar.setBackgroundColor(color);
        }
    }
}
