package cn.mvp.network.net.token;

/**
 * Created by qiji on 2022/2/28.
 */

public class RefreshTokenResponse {
    private String access_token;
    private String token_type;

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "RefreshTokenResponse{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                '}';
    }
}
