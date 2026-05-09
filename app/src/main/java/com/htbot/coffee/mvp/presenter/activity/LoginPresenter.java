package com.htbot.coffee.mvp.presenter.activity;

import android.content.Context;

import androidx.annotation.Nullable;

import com.htbot.coffee.base.BaseObserver;
import com.htbot.coffee.base.RxPresenter;
import com.htbot.coffee.base.interfaces.BasePresenter;
import com.htbot.coffee.mvp.MobileCountModel;
import com.htbot.coffee.mvp.presenter.contract.BaseContractView;
import com.htbot.coffee.net.ApiClient;
import com.htbot.coffee.net.ResponseData;
import com.htbot.coffee.net.api.BusinessApi;
import com.htbot.coffee.net.api.OperationApi;

/**
 * @author 53443
 */
public class LoginPresenter extends RxPresenter<BaseContractView<Object>>
        implements BasePresenter<BaseContractView<Object>> {

    private final OperationApi mAppApi;
    private final Context mContext;

    public LoginPresenter(Context context) {
        this.mAppApi = ApiClient.getApiService();
        this.mContext = context.getApplicationContext();
    }

    public void login(@Nullable String username, @Nullable String password) {
        addSubscribe(mAppApi.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseData<MobileCountModel>>(mContext, mView) {
                    @Override
                    public void onNext(ResponseData<MobileCountModel> bean) {
                        super.onNext(bean);
                        if (bean != null && bean.getCode() == 200) {
                            mView.updateUi(bean.getData(), 0);
                        } else {
                            mView.showError(bean == null ? "服务器返回为空" : bean.getMsg());
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