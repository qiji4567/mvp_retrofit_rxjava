package cn.mvp.network.net.common;

import java.lang.ref.WeakReference;


import android.app.Activity;

import androidx.annotation.NonNull;

import cn.mvp.network.dialog.LoadingDialog;
import cn.mvp.network.utils.LogUtils;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by qiji on 2022/2/28.
 */

public class ProgressUtils {
    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity, String msg) {
        final WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        final LoadingDialog dialogUtils = LoadingDialog.getLoadingDialog();
        if (!activityWeakReference.get().isFinishing()) {
            LogUtils.d("请求进度的上下文 " + activity.getClass().getSimpleName());
            dialogUtils.showProgress(activityWeakReference.get());
        }
        return upstream -> upstream.doOnSubscribe(disposable -> {

        }).doOnTerminate(() -> {
            Activity context;
            if ((context = activityWeakReference.get()) != null
                    && !context.isFinishing()) {
                dialogUtils.dismissProgress();
            }
        }).doOnSubscribe((Consumer<Disposable>) disposable -> {
//            Activity context;
//            if ((context = activityWeakReference.get()) != null
//                    && !context.isFinishing()) {
//                dialogUtils.dismissProgress();
//            }
        });
    }

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity) {
        return applyProgressBar(activity, "");
    }
}
