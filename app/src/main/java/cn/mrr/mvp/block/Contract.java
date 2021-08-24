package cn.mrr.mvp.block;

import cn.mrr.mvp.bean.IPLocationBean;
import cn.mrr.mvp.response.Response;
import io.reactivex.Observable;

/**
 * =================================================
 *
 * @author qi ji
 * @date 2021/8/23 14:43
 * @Email: 534438777@qq.com
 * @Content: =================================================
 */
public class Contract {

    public interface Presenter {

    }

    public interface IView {
        void getDataSuccess(Object json);

        void getDataFail();
    }

    public interface BaseModel {
         Observable<Response<IPLocationBean>> getIpLocation(String tvMac, String appVersion, String requestType);
    }

} 
 
 