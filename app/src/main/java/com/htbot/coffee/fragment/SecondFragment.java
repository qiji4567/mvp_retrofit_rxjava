package com.htbot.coffee.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.htbot.coffee.activity.ExitActivity;
import com.htbot.coffee.base.BaseFragment;
import com.htbot.coffee.databinding.Layout2Binding;

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
