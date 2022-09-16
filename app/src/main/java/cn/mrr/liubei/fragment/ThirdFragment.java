package cn.mrr.liubei.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import cn.mrr.liubei.R;
import cn.mrr.liubei.base.BaseMVPFragment;

/**
 * 功能描述
 *
 * @author qiji
 * @Description
 * @since 2022-09-15
 */
public class ThirdFragment extends BaseMVPFragment {
    private TextView mTvTitle;
    private View mFakeStatusBar;

    @Override
    protected int getLayoutId() {
        return R.layout.layout3;
    }

    @Override
    protected void initialize() {
        mTvTitle = (TextView) mView.findViewById(R.id.tv_title);
        mFakeStatusBar = mView.findViewById(R.id.fake_status_bar);
    }

    public void setTvTitleBackgroundColor(@ColorInt int color) {
        mTvTitle.setBackgroundColor(color);
        mFakeStatusBar.setBackgroundColor(color);
    }
}