package cn.mrr.liubei.dialog;

import androidx.fragment.app.FragmentActivity;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public final class LoadingDialog {

    private LoadingPopupView mProgressDialog;

    private static volatile LoadingDialog instance;

    private LoadingDialog() {
    }

    public static LoadingDialog getInstance() {
        if (instance == null) {
            synchronized (LoadingDialog.class) {
                if (instance == null) {
                    instance = new LoadingDialog();
                }
            }
        }
        return instance;
    }

    public void showProgress(FragmentActivity activity) {
        showProgress(activity, "加载中...");
    }

    public void showProgress(FragmentActivity activity, String msg) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }

        if (mProgressDialog == null) {
            mProgressDialog = (LoadingPopupView) new XPopup.Builder(activity)
                    .isLightNavigationBar(true)
                    .isViewMode(true)
                    .asLoading(msg == null || msg.length() == 0 ? "加载中..." : msg)
                    .show();
        } else if (!mProgressDialog.isShow()) {
            mProgressDialog.show();
        }
    }

    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.smartDismiss();
            mProgressDialog = null;
        }
    }
}