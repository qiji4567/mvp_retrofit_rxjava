package cn.mrr.liubei.mvp.presenter.activity;

import javax.inject.Inject;

import android.content.Context;

import androidx.annotation.Nullable;

import cn.mrr.liubei.base.BaseSubscriber;
import cn.mrr.liubei.base.RxPresenter;
import cn.mrr.liubei.base.interfaces.BasePresenter;
import cn.mrr.liubei.mvp.presenter.contract.BaseContractView;
import cn.mrr.liubei.net.IdeaApiService;
import cn.mvp.network.net.module.BaseBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 功能描述
 *
 * @author qiji
 * @Description
 * @since 2022-09-16
 */
public class LoginPresenter extends RxPresenter<BaseContractView> implements BasePresenter<BaseContractView> {

    private IdeaApiService mAppApi;
    private Context mContext;

    @Inject
    public LoginPresenter(IdeaApiService mAppApi, Context mContext) {
        this.mAppApi = mAppApi;
        this.mContext = mContext;
    }

    public void login( @Nullable String username, @Nullable String password) {
        addSubscribe(mAppApi.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseBean>(mContext, mView) {
                    @Override
                    public void onNext(BaseBean bean) {
                        super.onNext(bean);
                        if (200 == bean.getCode()) {
                            mView.updateUi(bean.getData(), 0);
                        } else {
                            mView.showError(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError() {
                        super.onError();
                        mView.showError("网络处理异常");
                    }
                }));

    }
}