package cn.mrr.liubei.base;

import javax.inject.Inject;

import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import cn.mrr.liubei.activity.ExitActivity;
import cn.mrr.liubei.base.interfaces.BasePresenter;
import cn.mrr.liubei.base.interfaces.BaseView;
import cn.mvp.network.dialog.LoadingDialog;
import cn.mvp.network.utils.LogUtils;

public abstract class BaseMVPActivity<T extends BasePresenter> extends BaseActivity implements BaseView {


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
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        if (null != mPresenter) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (null != mPresenter) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void showMsg(CharSequence msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//        SnackBarUtils.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), msg);
    }

    public void showContent(CharSequence msg) {
        Toast toast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void showError(CharSequence error) {
        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
//        SnackBarUtils.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), error);
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
//        if (null == loadingDialog) {
//            loadingDialog = new LoadingDialog(mContext);
//        }
//        if (!loadingDialog.isShowing()) {
//            loadingDialog.show();
//        }
        LoadingDialog.getLoadingDialog().showProgress(mContext);
    }

    @Override
    public synchronized void stopLoading() {
        LogUtils.e("销毁弹框名字 =  ", this.getClass().getSimpleName());
//        if (null != loadingDialog && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//            loadingDialog = null;
//        }
        LoadingDialog.getLoadingDialog().dismissProgress();
    }
}
