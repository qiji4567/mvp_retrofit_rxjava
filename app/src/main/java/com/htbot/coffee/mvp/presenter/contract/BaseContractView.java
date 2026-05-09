package com.htbot.coffee.mvp.presenter.contract;


import com.htbot.coffee.base.interfaces.BaseView;

public interface BaseContractView<T> extends BaseView {
        void updateUi(T bean, int typeCode);
}
