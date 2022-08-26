package cn.mrr.liubei.mvp.presenter.contract;


import cn.mrr.liubei.base.interfaces.BaseView;

public interface BaseContractView<T> extends BaseView {
        void updateUi(T bean, int typeCode);
}
