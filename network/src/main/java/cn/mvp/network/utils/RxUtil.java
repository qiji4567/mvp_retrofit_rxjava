package cn.mvp.network.utils;

import androidx.fragment.app.FragmentActivity;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import cn.mvp.network.net.common.ProgressUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    /**
     * Activity 统一线程处理，可选 Loading
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper(
            final RxAppCompatActivity activity,
            final boolean showLoading
    ) {
        if (activity == null) {
            return rxSchedulerHelper();
        }

        return observable -> {
            Observable<T> compose = observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(activity.bindUntilEvent(ActivityEvent.DESTROY));

            if (showLoading) {
                return compose.compose(ProgressUtils.<T>applyProgressBar(activity));
            }

            return compose;
        };
    }

    /**
     * Fragment 统一线程处理，可选 Loading
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper(
            final RxFragment fragment,
            final boolean showLoading
    ) {
        if (fragment == null || fragment.getActivity() == null) {
            return rxSchedulerHelper();
        }

        return observable -> {
            Observable<T> compose = observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY));

            if (showLoading) {
                FragmentActivity activity = fragment.getActivity();
                if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                    return compose;
                }
                return compose.compose(ProgressUtils.<T>applyProgressBar(activity));
            }

            return compose;
        };
    }

    /**
     * 统一线程处理，不显示 Loading
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}