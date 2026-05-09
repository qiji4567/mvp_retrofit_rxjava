package cn.mrr.liubei.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    protected VB binding;
    protected AppCompatActivity mActivity;
    protected Context mContext;
    protected boolean isInited = false;

    protected abstract VB getViewBinding(LayoutInflater inflater, ViewGroup container);

    protected abstract void initialize();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = getViewBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!isInited) {
            isInited = true;
            initialize();
        }
    }

    public VB getBinding() {
        return binding;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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