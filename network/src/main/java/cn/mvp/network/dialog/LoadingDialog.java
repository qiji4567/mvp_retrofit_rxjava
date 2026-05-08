package cn.mvp.network.dialog;

import androidx.fragment.app.FragmentActivity;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

/**
 * Created by qiji on 2017/5/26.
 * Description:
 */
public class LoadingDialog {

    private LoadingPopupView mProgressDialog;

    private static volatile LoadingDialog loadingDialog;

    private LoadingDialog() {
    }

    public static LoadingDialog getLoadingDialog() {
        if (loadingDialog == null) {
            synchronized (LoadingDialog.class) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog();
                }
            }
        }
        return loadingDialog;
    }

    /**
     * 显示 ProgressDialog
     */
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
        } else {
            mProgressDialog.setTitle(msg == null || msg.length() == 0 ? "加载中..." : msg);
            if (!mProgressDialog.isShow()) {
                mProgressDialog.show();
            }
        }
    }

    /**
     * 显示 ProgressDialog
     */
    public void showProgress(FragmentActivity activity) {
        showProgress(activity, "加载中...");
    }

    /**
     * 取消 ProgressDialog
     */
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.smartDismiss();
            mProgressDialog = null;
        }
    }
}