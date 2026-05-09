package com.htbot.coffee.mvp.presenter.activity;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.htbot.coffee.base.BaseObserver;
import com.htbot.coffee.base.RxPresenter;
import com.htbot.coffee.base.interfaces.BasePresenter;
import com.htbot.coffee.mvp.MobileCountModel;
import com.htbot.coffee.mvp.presenter.contract.BaseContractView;
import com.htbot.coffee.net.ApiClient;
import com.htbot.coffee.net.ApiService;
import com.htbot.coffee.net.BaseBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends RxPresenter<BaseContractView<MobileCountModel>>
        implements BasePresenter<BaseContractView<MobileCountModel>> {

    private final ApiService mAppApi;
    private final Context mContext;

    public MainPresenter(Context context) {
        this.mAppApi = ApiClient.getApiService();
        this.mContext = context.getApplicationContext();
    }

    public void login(AppCompatActivity activity, @Nullable String username, @Nullable String password) {
        addSubscribe(mAppApi.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<BaseBean<MobileCountModel>>(mContext, mView) {
                    @Override
                    public void onNext(BaseBean<MobileCountModel> bean) {
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