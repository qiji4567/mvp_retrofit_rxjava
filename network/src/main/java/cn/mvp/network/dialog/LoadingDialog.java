package cn.mvp.network.dialog;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import cn.mvp.network.R;


/**
 * Created by qiji on 2017/5/26.
 * Description:
 */

public class LoadingDialog {
    //  加载进度的dialog
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
     * 显示ProgressDialog
     */
    public void showProgress(Activity activity, String msg) {
       /* if (context == null || context.isFinishing()) {
            return;
        }*/
        //在中间弹出的Loading加载框
        if (mProgressDialog == null) {
            mProgressDialog = (LoadingPopupView) new XPopup.Builder(activity)
                    .isLightNavigationBar(true)
                    .isViewMode(true)
//                            .asLoading(null, R.layout.custom_loading_popup)
                    .asLoading(msg)
                    .show();
        } else {
            mProgressDialog.show();
        }
    }

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        //在中间弹出的Loading加载框
        if (mProgressDialog == null) {
            mProgressDialog = (LoadingPopupView) new XPopup.Builder(activity)
                    .isLightNavigationBar(true)
                    .isViewMode(true)
                    .asLoading(null, R.layout.dialog_progress)
                    .show();
            ImageView loadingImageView = mProgressDialog.findViewById(R.id.loadingImageView);
            Glide.with(activity).asGif().load(R.drawable.progress).into(loadingImageView);//icon为gif图
            TextView tv_loadingmsg = mProgressDialog.findViewById(R.id.tv_loadingmsg);
            tv_loadingmsg.setText("加载中...");
        }
    }

    /**
     * 取消ProgressDialog
     */
    public void dismissProgress() {
        if (mProgressDialog != null) {
//            LogUtils.e("自定义弹框消失 dismissProgress");
            mProgressDialog.smartDismiss();
            mProgressDialog = null;
        }
    }
}
