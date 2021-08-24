package cn.mrr.mvp.network.request;

import cn.mrr.mvp.bean.IPLocationBean;
import cn.mrr.mvp.response.Response;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * =================================================
 *
 * @author qi ji
 * @date 2021/8/23 11:08
 * @Email: 534438777@qq.com
 * 封装请求的接口
 * @Content: =================================================
 */
public interface Request {

     public static final String HOST = "https://softmbh.komect.com";

    /**
     * 获取ip地址接口配置
     *
     * @return
     */
    @GET("/softmbh-app/v2/ipLocation?")
    Observable<Response<IPLocationBean>> getLocation(@Query("tvMac") String tvMac, @Query("appVersion") String appVersion, @Query("requestType") String requestType);




}
