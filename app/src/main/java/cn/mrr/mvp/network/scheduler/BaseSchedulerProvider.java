package cn.mrr.mvp.network.scheduler;

import androidx.annotation.NonNull;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;

/**
 * =================================================
 *
 * @author qi ji
 * @date 2021/8/23 16:16
 * @Email: 534438777@qq.com
 * @Content: =================================================
 */
public class BaseSchedulerProvider implements BaseScheduler {

    @Nullable
    private static volatile BaseSchedulerProvider INSTANCE;

    public static BaseSchedulerProvider getInstance() {
        if (INSTANCE == null) {
            synchronized (BaseSchedulerProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BaseSchedulerProvider();
                }
            }
        }
        return INSTANCE;
    }


    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @NonNull
    @Override
    public <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(io())
                .subscribeOn(ui());
    }
}
 
 