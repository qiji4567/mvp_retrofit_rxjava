package com.htbot.coffee.base;

import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import androidx.viewbinding.ViewBinding;

import com.htbot.coffee.activity.ExitActivity;
import com.htbot.coffee.base.interfaces.BasePresenter;
import com.htbot.coffee.base.interfaces.BaseView;
import com.htbot.coffee.dialog.LoadingDialog;

public abstract class BaseMVPActivity<VB extends ViewBinding, T extends BasePresenter>
        extends BaseActivity<VB> implements BaseView {

    protected T mPresenter;

    protected abstract T createPresenter();

    @Override
    protected void onViewCreated() {
        super.onViewCreated();

        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }

        super.onDestroy();
    }

    @Override
    public void showMsg(CharSequence msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void showContent(CharSequence msg) {
        Toast toast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void showError(CharSequence error) {
        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showExit() {
        Intent intent = new Intent(mContext, ExitActivity.class);
        startActivity(intent);
    }

    public void showToast(CharSequence msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyView() {
    }

    @Override
    public void showErrorView() {
    }

    @Override
    public synchronized void startLoading() {
        LoadingDialog.getInstance().showProgress(this);
    }

    @Override
    public synchronized void stopLoading() {
        LoadingDialog.getInstance().dismissProgress();
    }
}