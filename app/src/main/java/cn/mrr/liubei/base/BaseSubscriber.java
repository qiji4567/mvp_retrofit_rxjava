package cn.mrr.liubei.base;

import android.content.Context;


import cn.mrr.liubei.base.interfaces.BaseView;
import cn.mvp.network.utils.NetworkUtils;
import io.reactivex.subscribers.ResourceSubscriber;

public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {

    private Context context;

    private BaseView view;


    public BaseSubscriber(Context context) {
        this.context = context;
    }

    public BaseSubscriber(Context context, BaseView view) {
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
        view.stopLoading();
    }

    @Override
    public void onError(Throwable t) {
        view.stopLoading();
        if (!NetworkUtils.isConnected()) {
            return;
        }
        onError();
    }

    public void onError() {}


    @Override
    public void onComplete() {}

}
