package cn.mrr.liubei.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.mrr.liubei.activity.ExitActivity;
import cn.mrr.liubei.base.interfaces.BasePresenter;
import cn.mrr.liubei.base.interfaces.BaseView;
import cn.mrr.liubei.dialog.LoadingDialog;

/**
 * @author 53443
 */
public abstract class BaseMVPFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    protected T mPresenter;

    protected abstract T createPresenter();

    private long clickTime = 0;

    public boolean onViewClick() {
        if (System.currentTimeMillis() - 1000 < clickTime) {
            showMsg("请不要频繁点击");
            return true;
        }
        clickTime = System.currentTimeMillis();
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }

    @Override
    public void showMsg(CharSequence msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(CharSequence error) {
        Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showExit() {
        Intent intent = new Intent(mContext, ExitActivity.class);
        startActivity(intent);
    }

    public void showToast(CharSequence msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyView() {
    }

    @Override
    public void showErrorView() {
    }

    @Override
    public void startLoading() {
        LoadingDialog.getInstance().showProgress(mActivity);
    }

    @Override
    public void stopLoading() {
        LoadingDialog.getInstance().dismissProgress();
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