package cn.mrr.liubei.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    protected View mView;
    protected AppCompatActivity mActivity;
    protected Context mContext;
    protected boolean isInited = false;

    protected abstract int getLayoutId();

    protected abstract void initialize();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isInited) {
            isInited = true;
            initialize();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mView = null;
    }

    public void startActivity(AppCompatActivity from, Class<?> to, boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        startActivity(intent);
        if (isFinish) {
            from.finish();
        }
    }

    protected void startActivity(AppCompatActivity from, Class<?> to, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(from, to);
        startActivity(intent);
        if (isFinish) {
            from.finish();
        }
    }
}
