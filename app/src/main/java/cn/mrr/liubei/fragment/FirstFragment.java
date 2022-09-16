package cn.mrr.liubei.fragment;

import android.view.View;
import android.widget.TextView;

import cn.mrr.liubei.R;
import cn.mrr.liubei.activity.ImageViewActivity;
import cn.mrr.liubei.base.BaseMVPFragment;

public class FirstFragment extends BaseMVPFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.layout1;
    }

    @Override
    protected void initialize() {
        TextView textView = mView.findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mActivity, ImageViewActivity.class, false);
            }
        });
    }


}