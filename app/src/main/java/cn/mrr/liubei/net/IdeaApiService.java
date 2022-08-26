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

//    /**
//     * 挂号登记-上传多张图片
//     *
//     * @param
//     * @接口负责人 张凯
//     */
//    @Multipart
//    @POST("/ms-tenant-consummer/user/{version}/uploadFile")
//    Observable<UpLoadFileBean> getUploadFile(@Part List<MultipartBody.Part> file);


    /**
     * 断奶登记获取生产批次列表
     *
     * @接口责任人 牛鹏涛，张凯
     */

    //登录接口-账号密码
    @POST("mobile/login")
    Flowable<BaseBean> loginFlowable(@Query("username") @NonNull String username,
                             @Query("password") @NonNull String password);

    //登录接口-账号密码
    @POST("mobile/login")
    Observable<BaseBean> loginObservable(@Query("username") @NonNull String username,
                               @Query("password") @NonNull String password);
//
//    /**
//     * 首页-设置页面退出登录
//     *
//     * @接口负责人 张凯
//     */
//    @DELETE("/ms-sso-auth-server/oauth/removeToken")
//    Observable<RemoveTokenBean> removeToken();
//
//
//    /**
//     * 阉割计划-批量登记阉割/留种的猪
//     *
//     * @接口负责人 牛彭涛
//     */
//    @POST("ms-pda-consummer/PigCastration/v1/updateBatchCastrationPlan")
//    Observable<BasicResponse> updateBatchCastrationPlan(@Body Map<String, Object> map);


    //----------------------历史记录 start


}
