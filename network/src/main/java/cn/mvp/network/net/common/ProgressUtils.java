package cn.mvp.network.net.common;

import java.lang.ref.WeakReference;


import androidx.fragment.app.FragmentActivity;

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
            @NonNull final FragmentActivity activity, String msg) {
        final WeakReference<FragmentActivity> activityWeakReference = new WeakReference<>(activity);
        final LoadingDialog dialogUtils = LoadingDialog.getLoadingDialog();
        if (!activityWeakReference.get().isFinishing()) {
            LogUtils.d("请求进度的上下文 " + activity.getClass().getSimpleName());
            dialogUtils.showProgress(activityWeakReference.get());
        }
        return upstream -> upstream.doOnSubscribe(disposable -> {

        }).doOnTerminate(() -> {
            FragmentActivity context;
            if ((context = activityWeakReference.get()) != null
                    && !context.isFinishing()) {
                dialogUtils.dismissProgress();
            }
        }).doOnSubscribe((Consumer<Disposable>) disposable -> {
//            FragmentActivity context;
//            if ((context = activityWeakReference.get()) != null
//                    && !context.isFinishing()) {
//                dialogUtils.dismissProgress();
//            }
        });
    }

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final FragmentActivity activity) {
        return applyProgressBar(activity, "");
    }
}
