package cn.mrr.liubei.mvp.presenter.activity;

import javax.inject.Inject;

import android.content.Context;

import androidx.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import cn.mrr.liubei.base.RxPresenter;
import cn.mrr.liubei.base.interfaces.BasePresenter;
import cn.mrr.liubei.mvp.presenter.contract.BaseContractView;
import cn.mrr.liubei.net.IdeaApiService;
import cn.mrr.liubei.net.RetrofitHelper;
import cn.mvp.network.net.common.ResponseObserver;
import cn.mvp.network.net.module.BaseBean;
import cn.mvp.network.utils.RxUtil;
import cn.mvp.network.utils.SPUtils;

/**
 * 功能描述
 *
 * @author qiji
 * @Description
 * @since 2022-08-23
 */
public class MainPresenter extends RxPresenter<BaseContractView> implements BasePresenter<BaseContractView> {

    private IdeaApiService mAppApi;
    private Context mContext;

    @Inject
    public MainPresenter(IdeaApiService mAppApi, Context mContext) {
        this.mAppApi = mAppApi;
        this.mContext = mContext;
    }

    public void login(RxAppCompatActivity activity, @Nullable String username, @Nullable String password) {
//        addSubscribe(mAppApi.login(username, password)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new BaseSubscriber<BaseBean>(mContext, mView) {
//                    @Override
//                    public void onNext(BaseBean bean) {
//                        super.onNext(bean);
//                        if (200 == bean.getCode()) {
//                            mView.updateUi(bean.getData(), 0);
//                        } else {
//                            mView.showError(bean.getMsg());
//                        }
//                    }
//
//                    @Override
//                    public void onError() {
//                        super.onError();
//                        mView.showError("网络处理异常");
//                    }
//                }));

        RetrofitHelper.getApiService()
                .loginObservable(username, password)
                .compose(RxUtil.rxSchedulerHelper(activity, true))
                .subscribe(new ResponseObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean response) {
                        //                        保存数据
                        SPUtils.saveObject(mContext, response);
                        mView.updateUi(response.getData(), 100);
                    }

                    @Override
                    public void onFail(String message) {
                        super.onFail(message);
                        mView.showError(message);
                    }
                });
    }

}
