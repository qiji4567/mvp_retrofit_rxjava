package cn.mrr.liubei.base.interfaces;

public interface BaseView<T> {

    void showMsg(CharSequence msg);

    void showError(CharSequence error);

    void showExit();

    void showEmptyView();

    void showErrorView();

    void startLoading();

    void stopLoading();

}
