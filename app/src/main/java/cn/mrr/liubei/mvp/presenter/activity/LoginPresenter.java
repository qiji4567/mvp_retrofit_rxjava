package cn.mrr.liubei.mvp.presenter.activity;

import android.content.Context;

import androidx.annotation.Nullable;

import cn.mrr.liubei.base.BaseObserver;
import cn.mrr.liubei.base.RxPresenter;
import cn.mrr.liubei.base.interfaces.BasePresenter;
import cn.mrr.liubei.mvp.MobileCountModel;
import cn.mrr.liubei.mvp.presenter.contract.BaseContractView;
import cn.mrr.liubei.net.ApiClient;
import cn.mrr.liubei.net.ApiService;
import cn.mrr.liubei.net.BaseBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 53443
 */
public class LoginPresenter extends RxPresenter<BaseContractView<Object>>
        implements BasePresenter<BaseContractView<Object>> {

    private final ApiService mAppApi;
    private final Context mContext;

    public LoginPresenter(Context context) {
        this.mAppApi = ApiClient.getApiService();
        this.mContext = context.getApplicationContext();
    }

    public void login(@Nullable String username, @Nullable String password) {
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