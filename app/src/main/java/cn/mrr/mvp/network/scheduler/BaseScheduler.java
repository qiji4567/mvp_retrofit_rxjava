package cn.mrr.mvp.network.scheduler;

import androidx.annotation.NonNull;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;

/**
 * =================================================
 *
 * @author qi ji
 * @date 2021/8/23 16:15
 * @Email: 534438777@qq.com
 * @Content: =================================================
 */
public interface BaseScheduler {


    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    <T> ObservableTransformer<T, T> applySchedulers();

}
 
 