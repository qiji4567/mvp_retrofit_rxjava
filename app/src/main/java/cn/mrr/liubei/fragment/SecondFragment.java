package cn.mrr.liubei.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import cn.mrr.liubei.R;
import cn.mrr.liubei.activity.ExitActivity;
import cn.mrr.liubei.activity.ImageViewActivity;
import cn.mrr.liubei.base.BaseMVPFragment;

/**
 * 功能描述
 *
 * @author qiji
 * @Description
 * @since 2022-09-15
 */
public class SecondFragment extends BaseMVPFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.layout2;
    }

    @Override
    protected void initialize() {
        TextView textView = mView.findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mActivity, ExitActivity.class, false);
            }
        });
    }
}