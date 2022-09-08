
package cn.mvp.network.net.token;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import android.text.TextUtils;

import cn.mvp.network.net.common.CommonService;
import cn.mvp.network.net.common.ResponseObserver;
import cn.mvp.network.net.common.RetrofitService;
import cn.mvp.network.net.exception.RefreshTokenExpiredException;
import cn.mvp.network.net.exception.TokenExpiredException;
import cn.mvp.network.net.module.BaseRequest;
import cn.mvp.network.net.module.LoginResponse;
import cn.mvp.network.utils.ApplicationContextUtils;
import cn.mvp.network.utils.SPUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static cn.mvp.network.utils.RSACryptography.PUBLIC_KEY;
import static cn.mvp.network.utils.RSACryptography.encrypt;
import static cn.mvp.network.utils.RSACryptography.getPublicKey;

public class ProxyHandler implements InvocationHandler {

    private final static String ACCESS_TOKEN = "access_token";

    private final static int REFRESH_TOKEN_VALID_TIME = 30;
    private static long tokenChangedTime = 0;
    private Throwable mRefreshTokenError = null;
    private boolean needResetAccessToken;

    private final Object mProxyObject;
    private final IGlobalManager mGlobalManager;
    private final String mBaseUrl;

    public ProxyHandler(Object proxyObject, IGlobalManager globalManager, String baseUrl) {
        mBaseUrl = baseUrl;
        mProxyObject = proxyObject;
        mGlobalManager = globalManager;
    }

    /**
     * 通过refresh token换取新的access token和refresh token并存储到本地，
     * 刷新成功后将mIsTokenNeedReset置为true，后边请求会根据mIsTokenNeedReset来重置刷新后的token。
     */
    private Observable<?> refreshTokenWhenTokenInvalid() {
        synchronized (ProxyHandler.class) {

            if (new Date().getTime() - tokenChangedTime < REFRESH_TOKEN_VALID_TIME) {
                needResetAccessToken = true;
                return Observable.just(true);
            } else {
                String username = (String) SPUtils.get(ApplicationContextUtils.getInstance(), "username", "");
                String password = (String) SPUtils.get(ApplicationContextUtils.getInstance(), "password", "");
                LoginResponse loginResponse = (LoginResponse) SPUtils.getObject(ApplicationContextUtils.getInstance(), LoginResponse.class);
                String refresh_token = "";
                if (loginResponse != null && loginResponse.getData() != null) {
                    refresh_token = loginResponse.getData().getRefresh_token();
                }
                try {
                    //获取公钥
                    PublicKey publicKey = getPublicKey(PUBLIC_KEY);
                    //公钥加密
                    byte[] encrypted = encrypt(password.getBytes(), publicKey);
                    //公钥用base64编码
                    String encodePublic = Base64.encodeBase64String(encrypted);
                    String scope = "sysoperate",
                            grant_type = "password",
                            client_id = "sysoperate",
                            client_secret = "123456";
                    RetrofitService
                            .getRetrofitBuilder(mBaseUrl)
                            .build()
                            .create(CommonService.class)
                            .refreshToken(scope, grant_type, username, encodePublic, client_id, client_secret, refresh_token)
                            .subscribe(new ResponseObserver<LoginResponse>() {
                                @Override
                                public void onSuccess(LoginResponse response) {
                                    if (response != null) {
                                        mGlobalManager.tokenRefresh(response);
                                        needResetAccessToken = true;
                                        tokenChangedTime = new Date().getTime();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    super.onError(e);
                                    mRefreshTokenError = e;
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mRefreshTokenError != null) {
                    Observable<Object> error = Observable.error(mRefreshTokenError);
                    // 这里必须将mRefreshTokenError置空，否则会有问题。
                    mRefreshTokenError = null;
                    return error;
                } else {
                    return Observable.just(true);
                }
            }
        }
    }

    /**
     * Update the access token of the args in the method.
     * <p>
     * access token刷新成功后，再次发起请求需要使用刷新后的access token，
     * 这个方法会拦截本次请求，根据请求方法注入新的access token。
     */
    @SuppressWarnings("unchecked")
    private void updateMethodToken(Method method, Object[] args) {
        String token = (String) SPUtils.get(ApplicationContextUtils.getInstance(), "access_token", "");
        if (needResetAccessToken && !TextUtils.isEmpty(token)) {
            Annotation[][] annotationsArray = method.getParameterAnnotations();
            Annotation[] annotations;
            if (annotationsArray != null && annotationsArray.length > 0) {
                for (int i = 0; i < annotationsArray.length; i++) {
                    annotations = annotationsArray[i];
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof FieldMap || annotation instanceof QueryMap) {
                            if (args[i] instanceof Map)
                                ((Map<String, Object>) args[i]).put(ACCESS_TOKEN, token);
                        } else if (annotation instanceof Query) {
                            if (ACCESS_TOKEN.equals(((Query) annotation).value()))
                                args[i] = token;
                        } else if (annotation instanceof Field) {
                            if (ACCESS_TOKEN.equals(((Field) annotation).value()))
                                args[i] = token;
                        } else if (annotation instanceof Part) {
                            if (ACCESS_TOKEN.equals(((Part) annotation).value())) {
                                RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
                                args[i] = tokenBody;
                            }
                        } else if (annotation instanceof Body) {
                            if (args[i] instanceof BaseRequest) {
                                BaseRequest requestData = (BaseRequest) args[i];
                                requestData.setToken(token);
                                args[i] = requestData;
                            }
                        }
                    }
                }
            }
            needResetAccessToken = false;
        }
    }

    @Override
    public Object invoke(Object proxy, final Method method, final Object[] args) {
        return Observable.just(true).flatMap((Function<Object, ObservableSource<?>>) o -> {
            try {
                try {
                    /**
                     * 如果mIsTokenNeedReset为true,则说明本次请求是在成功access token成功刷新后
                     * 自动调用，而此时方法中的access token参数仍然为旧的access token，因此需要
                     * 将这个方法中的access token参数重置为刷新后的access token参数
                     */
                    if (needResetAccessToken) {
                        updateMethodToken(method, args);
                    }
                    return (Observable<?>) method.invoke(mProxyObject, args);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).retryWhen(observable -> observable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
            if (throwable instanceof TokenExpiredException) { // 捕捉到token过期异常，则需要刷新token
                return refreshTokenWhenTokenInvalid();
            } else if (throwable instanceof RefreshTokenExpiredException) {
                // Token 不存在，执行退出登录的操作。（为了防止多个请求，都出现 Token 不存在的问题，
                // 这里需要取消当前所有的网络请求）
                mGlobalManager.logout();
                return Observable.error(throwable);
            }
            return Observable.error(throwable);
        }));
    }
}
