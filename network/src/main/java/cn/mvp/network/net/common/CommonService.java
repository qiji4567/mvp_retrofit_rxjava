package cn.mvp.network.net.common;



import cn.mvp.network.net.module.LoginResponse;
import cn.mvp.network.net.token.RefreshTokenResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dell on 2017/4/1.
 */

public interface CommonService {

    @POST("/ms-sso-auth-server/oauth/token?")
    Observable<LoginResponse> refreshToken(@Query("scope") String scope
            , @Query("grant_type") String grant_type
            , @Query("username") String username
            , @Query("password") String password
            , @Query("client_id") String client_id
            , @Query("client_secret") String client_secret
            , @Query("refresh_token") String refresh_token);
}
