package com.htbot.coffee.mvp.presenter.activity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.htbot.coffee.application.MyApplication;
import com.htbot.coffee.base.RxPresenter;
import com.htbot.coffee.mvp.presenter.contract.LoginContract;
import com.htbot.coffee.net.ResponseData;
import com.htbot.coffee.net.api.OperationApi;
import com.htbot.coffee.utils.AESUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 登录 Presenter
 * <p>
 * 负责：
 * 1. 登录参数校验
 * 2. 获取公钥
 * 3. 加密密码
 * 4. 调用登录接口
 * 5. 通知 View 刷新 UI
 *
 * @author 53443
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    private static final String PLATFORM_ANDROID = "android";
    private static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";

    private final Context appContext;

    public LoginPresenter(@NonNull Context context) {
        this.appContext = context.getApplicationContext();
    }

    @Override
    public void login(@Nullable String account, @Nullable String password) {
        String finalAccount = account == null ? "" : account.trim();
        String finalPassword = password == null ? "" : password.trim();

        if (StringUtils.isEmpty(finalAccount)) {
            if (isViewAttached()) {
                mView.onUsernameEmpty();
            }
            return;
        }

        if (StringUtils.isEmpty(finalPassword)) {
            if (isViewAttached()) {
                mView.onPasswordEmpty();
            }
            return;
        }

        if (isViewAttached()) {
            mView.startLoading();
        }

        addSubscribe(
                OperationApi.getPublicKey()
                        .subscribeOn(Schedulers.io())
                        .flatMap(publicKeyResponse -> {
                            String publicKey = publicKeyResponse == null ? null : publicKeyResponse.getData();

                            if (StringUtils.isEmpty(publicKey)) {
                                throw new IllegalStateException("公钥获取失败");
                            }

                            RequestBody requestBody = buildLoginRequestBody(finalAccount, finalPassword, publicKey);
                            return OperationApi.login(requestBody);
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    mView.stopLoading();
                                    handleLoginResponse(response);
                                },
                                throwable -> {
                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    mView.stopLoading();
                                    String message = throwable == null || StringUtils.isEmpty(throwable.getMessage())
                                            ? "网络异常，请稍后重试"
                                            : throwable.getMessage();
                                    mView.showError(message);
                                }
                        )
        );
    }

    private RequestBody buildLoginRequestBody(
            @NonNull String account,
            @NonNull String password,
            @NonNull String publicKey
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put("account", account);
        params.put("password", AESUtils.encryptUserAndPwd(password, publicKey));
        params.put("serialNumber", MyApplication.instance.getSerialNumber());
        params.put("platform", PLATFORM_ANDROID);

        String bodyJson = AESUtils.createRequestOperateBody(params);

        return RequestBody.create(
                MediaType.parse(MEDIA_TYPE_JSON),
                bodyJson
        );
    }

    private void handleLoginResponse(ResponseData response) {
        if (response == null) {
            mView.showError("服务器返回为空");
            return;
        }

        if (response.getSuccess() && !StringUtils.isEmpty(String.valueOf(response.getData()))) {
            mView.onLoginSuccess(String.valueOf(response.getData()));
            return;
        }

        String message = response.getMessage();
        if (StringUtils.isEmpty(message)) {
            message = "登录失败，请检查账号或密码";
        }
        mView.showError(message);
    }

    private boolean isViewAttached() {
        return mView != null;
    }
}