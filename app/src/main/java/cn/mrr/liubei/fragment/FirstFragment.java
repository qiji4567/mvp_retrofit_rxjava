package cn.mrr.liubei.fragment;

import cn.mrr.liubei.R;
import cn.mrr.liubei.activity.ImageViewActivity;
import cn.mrr.liubei.base.BaseMVPFragment;
import cn.mrr.liubei.databinding.Layout1Binding;

public class FirstFragment extends BaseMVPFragment {
    private Layout1Binding binding;

    @Override
    protected int getLayoutId() {
        return R.layout.layout1;
    }

    @Override
    protected void initialize() {
        binding = Layout1Binding.bind(mView);
        binding.text.setOnClickListener(v -> startActivity(mActivity, ImageViewActivity.class, false));
    }
}
