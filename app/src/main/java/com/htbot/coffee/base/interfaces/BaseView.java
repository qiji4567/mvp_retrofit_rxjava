package com.htbot.coffee.base.interfaces;

/**
 * @author 53443
 */
public interface BaseView<T> {

    /**
     * 显示提示信息
     *
     * @param msg 提示信息
     */
    void showMsg(CharSequence msg);

    /**
     * 显示错误信息
     *
     * @param error 错误信息
     */
    void showError(CharSequence error);

    /**
     * 显示退出信息
     */
    void showExit();

    /**
     * 显示空页面
     */
    void showEmptyView();

    /**
     * 显示错误页面
     */
    void showErrorView();

    /**
     * 显示加载进度
     */
    void startLoading();

    /**
     * 停止加载进度
     */
    void stopLoading();

}
