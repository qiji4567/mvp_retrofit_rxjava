package cn.mrr.liubei.net;

import androidx.annotation.NonNull;

import cn.mrr.liubei.mvp.MobileCountModel;
import cn.mvp.network.net.module.BaseBean;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dell on 2017/4/1.
 */

public interface IdeaApiService {


    /**
     * 断奶登记获取生产批次列表
     *
     * @接口责任人 牛鹏涛，张凯
     */
    @POST("mobile/login")
    Observable<BaseBean> loginFlowable(@Query("username") @NonNull String username,
                                       @Query("password") @NonNull String password);

    /**
     * 登录接口-账号密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @POST("mobile/login")
    Observable<BaseBean<MobileCountModel>> login(@Query("username") String username,
                                                 @Query("password") String password);

    /**
     * 登录接口-账号密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @POST("mobile/login")
    Observable<BaseBean> loginObservable(@Query("username") String username,
                                         @Query("password") String password);


}
