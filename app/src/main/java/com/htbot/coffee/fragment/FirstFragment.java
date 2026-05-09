package com.htbot.coffee.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.htbot.coffee.activity.ActionBarActivity;
import com.htbot.coffee.base.BaseFragment;
import com.htbot.coffee.databinding.Layout1Binding;

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
