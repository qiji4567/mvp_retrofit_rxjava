package cn.mrr.liubei.fragment;

import cn.mrr.liubei.R;
import cn.mrr.liubei.activity.ImageViewActivity;
import cn.mrr.liubei.base.BaseFragment;
import cn.mrr.liubei.databinding.Layout1Binding;

/**
 * @author 53443
 */
public class FirstFragment extends BaseFragment {
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
