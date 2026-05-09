package com.htbot.coffee.mvp.presenter.activity;

import android.content.Context;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.htbot.coffee.base.BaseObserver;
import com.htbot.coffee.base.RxPresenter;
import com.htbot.coffee.base.interfaces.BasePresenter;
import com.htbot.coffee.mvp.MobileCountModel;
import com.htbot.coffee.mvp.presenter.contract.BaseContractView;
import com.htbot.coffee.net.LoginRequest;
import com.htbot.coffee.net.ResponseData;
import com.htbot.coffee.net.api.OperationApi;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainPresenter extends RxPresenter<BaseContractView<MobileCountModel>>
        implements BasePresenter<BaseContractView<MobileCountModel>> {

    private final Context mContext;
    private final Gson gson = new Gson();

    public MainPresenter(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void login(@Nullable String username, @Nullable String password) {
        LoginRequest request = new LoginRequest(username, password);
        String json = gson.toJson(request);

        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json; charset=utf-8")
        );

        addSubscribe(OperationApi.login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseData>(mContext, mView) {
                    @Override
                    public void onNext(ResponseData bean) {
                        super.onNext(bean);

                        if (bean != null && bean.getCode() == 200) {
                            mView.updateUi((MobileCountModel) bean.getData(), 0);
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