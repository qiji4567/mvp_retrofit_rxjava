package cn.mrr.liubei.net;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {

    /**
     * 通用 GET。
     */
    @GET
    Observable<ResponseBody> get(
            @Url String url,
            @QueryMap Map<String, Object> params
    );

    /**
     * 通用 POST JSON。
     */
    @POST
    Observable<ResponseBody> post(
            @Url String url,
            @Body Object body
    );

    /**
     * 示例：登录接口。
     * 把 login 地址改成你后端真实接口路径。
     */
    @POST("login")
    Observable<ApiResponse<LoginResult>> login(@Body LoginRequest request);
}