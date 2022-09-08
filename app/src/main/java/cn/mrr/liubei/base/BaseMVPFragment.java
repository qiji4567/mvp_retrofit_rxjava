package cn.mrr.liubei.base;

import javax.inject.Inject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import cn.mrr.liubei.activity.ExitActivity;
import cn.mrr.liubei.base.interfaces.BasePresenter;
import cn.mrr.liubei.base.interfaces.BaseView;
import cn.mvp.network.dialog.LoadingDialog;


public abstract class BaseMVPFragment<T extends BasePresenter> extends BaseFragment implements BaseView {


    @Inject
    protected T mPresenter;

//    private LoadingDialog loadingDialog;
    private long clickTime = 0;

    public void initInject() {
    }

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
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public void showMsg(CharSequence msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
//        SnackBarUtils.show(((ViewGroup) mActivity.findViewById(android.R.id.content)).getChildAt(0), msg);
    }

    @Override
    public void showError(CharSequence error) {
        Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
//        SnackBarUtils.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), error);
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
//        if (null == loadingDialog) {
//            loadingDialog = new LoadingDialog(mActivity);
//        }
//        if (!loadingDialog.isShowing()) {
//            loadingDialog.show();
//        }
        LoadingDialog.getLoadingDialog().showProgress(mActivity);
    }

    @Override
    public void stopLoading() {
//        LogUtils.e("销毁弹框名字 =  ", this.getClass().getSimpleName());
//        if (null != loadingDialog && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//            loadingDialog = null;
//        }
        LoadingDialog.getLoadingDialog().dismissProgress();
    }

    public void showToast(String content) {
        Toast.makeText(mActivity, content, Toast.LENGTH_SHORT).show();

    }


    public void startActivity(android.app.Activity from, Class<?> to, boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        startActivity(intent);
        if (isFinish) {
            from.finish();
        }
    }

    // 带参数的Activity
    protected void startActivity(android.app.Activity from, Class<?> to, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent();
        if (bundle != null)
            intent.putExtras(bundle);
        intent.setClass(from, to);
        startActivity(intent);
        if (isFinish) {
            from.finish();
        }
    }
}
