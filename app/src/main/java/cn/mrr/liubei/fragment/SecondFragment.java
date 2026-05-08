package cn.mrr.liubei.fragment;

import cn.mrr.liubei.R;
import cn.mrr.liubei.activity.ExitActivity;
import cn.mrr.liubei.base.BaseMVPFragment;
import cn.mrr.liubei.databinding.Layout2Binding;

public class SecondFragment extends BaseMVPFragment {
    private Layout2Binding binding;
    @Override protected int getLayoutId() { return R.layout.layout2; }
    @Override protected void initialize() { binding = Layout2Binding.bind(mView); binding.text.setOnClickListener(v -> startActivity(mActivity, ExitActivity.class, false)); }
}
