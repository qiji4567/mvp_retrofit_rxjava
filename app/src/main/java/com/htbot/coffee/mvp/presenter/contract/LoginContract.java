package com.htbot.coffee.mvp.presenter.contract;

import androidx.annotation.NonNull;

import com.htbot.coffee.base.interfaces.BasePresenter;
import com.htbot.coffee.base.interfaces.BaseView;

/**
 * 登录模块契约类
 *
 * View：只负责 UI 展示、页面跳转
 * Presenter：只负责业务流程、参数校验、接口调用
 */
public interface LoginContract {

    interface View extends BaseView {

        /**
         * 登录成功
         */
        void onLoginSuccess(@NonNull String token);

        /**
         * 用户名为空
         */
        void onUsernameEmpty();

        /**
         * 密码为空
         */
        void onPasswordEmpty();
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 登录
         */
        void login(String account, String password);
    }
}