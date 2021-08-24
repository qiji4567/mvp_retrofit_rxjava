package cn.mrr.mvp.block;

import java.util.Map;

import cn.mrr.mvp.bean.IPLocationBean;
import cn.mrr.mvp.network.NetWorkManager;
import cn.mrr.mvp.response.Response;
import io.reactivex.Observable;

/**
 * =================================================
 *
 * @author qi ji
 * @date 2021/8/23 15:49
 * @Email: 534438777@qq.com
 * @Content: =================================================
 */
public class Model implements Contract.BaseModel {

    @Override
    public Observable<Response<IPLocationBean>> getIpLocation(String tvMac, String appVersion, String requestType) {
        return NetWorkManager.getRequest().getLocation(tvMac,appVersion,requestType);
    }

    @Override
    public Observable<Response<IPLocationBean>> getIpLocationMap(Map<String, Object> map) {
        return NetWorkManager.getRequest().getLocationMap(map);
    }


}
 
 