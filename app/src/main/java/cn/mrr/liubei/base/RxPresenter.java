package cn.mrr.liubei.base;

import javax.inject.Inject;

import com.google.gson.Gson;

import cn.mrr.liubei.base.interfaces.BasePresenter;
import cn.mrr.liubei.base.interfaces.BaseView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;

    protected CompositeDisposable mCompositeDisposable;

    public Gson gson;

    @Inject
    public RxPresenter(){
        if (null == gson){
            gson = new Gson();
        }
    }




    /**
     * 订阅事件
     *
     * @param
     */
    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 取消所有订阅
     */
    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }



    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }



}
