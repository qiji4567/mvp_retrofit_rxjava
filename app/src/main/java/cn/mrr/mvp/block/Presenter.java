package cn.mrr.mvp.block;

import android.util.Log;

import cn.mrr.mvp.bean.IPLocationBean;
import cn.mrr.mvp.network.scheduler.BaseSchedulerProvider;
import cn.mrr.mvp.response.ResponseTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * =================================================
 *
 * @author qi ji
 * @date 2021/8/24 08:43
 * @Email: 534438777@qq.com
 * @Content: =================================================
 */
public class Presenter {

    private static final String TAG = "Presenter";
    private Model model;

    private Contract.IView iView;

    private BaseSchedulerProvider baseSchedulerProvider;


    private CompositeDisposable mDisposable;

    public Presenter(Model model, Contract.IView iView, BaseSchedulerProvider baseSchedulerProvider) {
        this.model = model;
        this.iView = iView;
        this.baseSchedulerProvider = baseSchedulerProvider;
        this.mDisposable = new CompositeDisposable();
    }

    public void dispose() {
        mDisposable.dispose();
    }

    public void getIPLocation(String tvMac, String appVersion, String requestType) {
//        Disposable disposable = model.getIpLocation(tvMac, appVersion, requestType)
//                .compose(ResponseTransformer.handleResult())
//                .compose(baseSchedulerProvider.applySchedulers())
//                .subscribe(iPLocationBean -> {
//                    // 处理数据 直接获取到 iPLocationBean
//                    iView.getDataSuccess(iPLocationBean.toString());
//                }, Throwable -> {
//                    //异常
//                    iView.getDataFail();
//                });
//        mDisposable.add(disposable);
//
        Disposable disposable = model.getIpLocation(tvMac, appVersion, requestType)
                .compose(ResponseTransformer.handleResult())
                .compose(baseSchedulerProvider.applySchedulers())
                .subscribe(new Consumer<IPLocationBean>() {
                    @Override
                    public void accept(IPLocationBean ipLocationBean) throws Exception {
                        Log.e(TAG, "!!!!!!!!"+ipLocationBean.toString());
                        // 处理数据 直接获取到 iPLocationBean
                        iView.getDataSuccess(ipLocationBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //异常
                        iView.getDataFail();
                    }
                });
        mDisposable.add(disposable);
    }

}
 
 