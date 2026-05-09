package cn.mrr.liubei.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import cn.mrr.liubei.R;
import cn.mrr.liubei.activity.ExitActivity;
import cn.mrr.liubei.base.BaseFragment;
import cn.mrr.liubei.base.BaseMVPFragment;
import cn.mrr.liubei.databinding.Layout1Binding;
import cn.mrr.liubei.databinding.Layout2Binding;

public class SecondFragment extends BaseFragment<Layout2Binding> {


    @Override
    protected Layout2Binding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return Layout2Binding.inflate(inflater, container, false);
    }

    @Override
    protected void initialize() {
        binding.text.setOnClickListener(v -> startActivity(mActivity, ExitActivity.class, false));
    }
}
