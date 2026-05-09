package cn.mrr.liubei.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import cn.mrr.liubei.activity.ActionBarActivity;
import cn.mrr.liubei.base.BaseFragment;
import cn.mrr.liubei.databinding.Layout1Binding;

/**
 * @author 53443
 */
public class FirstFragment extends BaseFragment<Layout1Binding> {

    @Override
    protected Layout1Binding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return Layout1Binding.inflate(inflater, container, false);
    }

    @Override
    protected void initialize() {
        binding.text.setOnClickListener(v -> startActivity(mActivity, ActionBarActivity.class, false));
    }


}
