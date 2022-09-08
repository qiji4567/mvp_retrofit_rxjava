package cn.mrr.liubei.base.interfaces;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
