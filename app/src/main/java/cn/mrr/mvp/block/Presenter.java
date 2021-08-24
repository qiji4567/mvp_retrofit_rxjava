package cn.mrr.mvp.block;

import java.util.Map;

import cn.mrr.mvp.network.scheduler.BaseSchedulerProvider;
import cn.mrr.mvp.response.ResponseTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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


    public void getIPLocation(String tvMac, String appVersion, String requestType) {
        Disposable disposable = model.getIpLocation(tvMac, appVersion, requestType)
                .compose(ResponseTransformer.handleResult())
                .compose(baseSchedulerProvider.applySchedulers())
                .subscribe(iPLocationBean -> {
                    // 处理数据 直接获取到 iPLocationBean
                    iView.getDataSuccess(iPLocationBean);
                }, Throwable -> {
                    //异常
                    iView.getDataFail(Throwable.getMessage());
                });

        mDisposable.add(disposable);
    }


    public void getIPLocationMap(Map<String,Object> map) {
        Disposable disposable = model.getIpLocationMap(map)
                .compose(ResponseTransformer.handleResult())
                .compose(baseSchedulerProvider.applySchedulers())
                .subscribe(iPLocationBean -> {
                    // 处理数据 直接获取到 iPLocationBean
                    iView.getDataSuccess(iPLocationBean);
                }, Throwable -> {
                    //异常
                    iView.getDataFail(Throwable.getMessage());
                });

        mDisposable.add(disposable);
    }





    public void dispose() {
        mDisposable.dispose();
    }
}
 
 