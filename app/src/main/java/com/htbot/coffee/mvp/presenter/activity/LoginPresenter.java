package com.htbot.coffee.mvp.presenter.activity;

import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.htbot.coffee.application.MyApplication;
import com.htbot.coffee.base.BaseObserver;
import com.htbot.coffee.base.RxPresenter;
import com.htbot.coffee.base.interfaces.BasePresenter;
import com.htbot.coffee.mvp.presenter.contract.BaseContractView;
import com.htbot.coffee.net.ResponseData;
import com.htbot.coffee.net.api.OperationApi;
import com.htbot.coffee.utils.AESUtils;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 登录 Presenter
 * @author 53443
 */
public class LoginPresenter extends RxPresenter<BaseContractView>
        implements BasePresenter<BaseContractView> {

    public static final int TYPE_LOGIN_SUCCESS = 100;

    private static final String PLATFORM_ANDROID = "android";
    private static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";

    private final Context mContext;

    public LoginPresenter(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void login(String account, String password) {
        if (mView == null) {
            return;
        }

        account = account == null ? "" : account.trim();
        password = password == null ? "" : password.trim();

        if (StringUtils.isEmpty(account)) {
            mView.showMsg("请输入账号");
            return;
        }

        if (StringUtils.isEmpty(password)) {
            mView.showMsg("请输入密码");
            return;
        }

        mView.startLoading();

        final String finalAccount = account;
        final String finalPassword = password;

        addSubscribe(OperationApi.getPublicKey()
                .subscribeOn(Schedulers.io())
                .flatMap(pkResponse -> {
                    if (pkResponse == null || StringUtils.isEmpty(pkResponse.getData())) {
                        throw new IllegalStateException("公钥获取失败");
                    }

                    String publicKey = pkResponse.getData();

                    HashMap<String, Object> postMap = new HashMap<>();
                    postMap.put("account", finalAccount);
                    postMap.put("password", AESUtils.encryptUserAndPwd(finalPassword, publicKey));
                    postMap.put("serialNumber", MyApplication.instance.getSerialNumber());
                    postMap.put("platform", PLATFORM_ANDROID);

                    RequestBody body = RequestBody.create(
                            MediaType.parse(MEDIA_TYPE_JSON),
                            AESUtils.createRequestOperateBody(postMap)
                    );

                    return OperationApi.login(body);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseData<String>>(mContext, mView) {

                    @Override
                    public void onNext(ResponseData<String> bean) {
                        super.onNext(bean);

                        if (mView == null) {
                            return;
                        }

                        mView.stopLoading();

                        if (bean == null) {
                            mView.showError("服务器返回为空");
                            return;
                        }

                        if (bean.getSuccess() && !StringUtils.isEmpty(bean.getData())) {
                            mView.updateUi(bean.getData(), TYPE_LOGIN_SUCCESS);
                        } else {
                            mView.showError(StringUtils.isEmpty(bean.getMessage())
                                    ? "登录失败"
                                    : bean.getMessage());
                        }
                    }

                    @Override
                    public void onError() {
                        super.onError();

                        if (mView != null) {
                            mView.stopLoading();
                            mView.showError("网络处理异常");
                        }
                    }
                }));
    }
}