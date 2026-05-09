package com.htbot.coffee.base;

import android.content.Context;

import com.htbot.coffee.base.interfaces.BaseView;
import com.htbot.coffee.utils.NetworkUtils;

import io.reactivex.rxjava3.observers.DisposableObserver;


public abstract class BaseObserver<T> extends DisposableObserver<T> {

    private Context context;

    private BaseView view;


    public BaseObserver(Context context) {
        this.context = context;
    }

    public BaseObserver(Context context, BaseView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected() && view != null) {
            view.showError("无网络");
            onComplete();
            return;
        }
    }

    @Override
    public void onNext(T t) {
        if (view != null) {
            view.stopLoading();
        }
    }

    @Override
    public void onError(Throwable t) {
        if (view != null) {
            view.stopLoading();
        }
        if (!NetworkUtils.isConnected()) {
            return;
        }
        onError();
    }

    public void onError() {
    }


    @Override
    public void onComplete() {
    }

}
