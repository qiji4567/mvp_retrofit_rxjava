package cn.mrr.liubei.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;

import cn.mrr.liubei.R;
import cn.mrr.liubei.base.BaseFragment;
import cn.mrr.liubei.base.BaseMVPFragment;
import cn.mrr.liubei.databinding.Layout1Binding;
import cn.mrr.liubei.databinding.Layout3Binding;

public class ThirdFragment extends BaseFragment<Layout3Binding> {

    @Override
    protected Layout3Binding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return Layout3Binding.inflate(inflater, container, false);
    }

    @Override
    protected void initialize() {
        setTvTitleBackgroundColor(Color.RED);
    }

    public void setTvTitleBackgroundColor(@ColorInt int color) {
        if (binding != null) {
            binding.tvTitle.setBackgroundColor(color);
            binding.fakeStatusBar.setBackgroundColor(color);
        }
    }
}
