package cn.mvp.network.net.token;

import cn.mvp.network.net.module.LoginResponse;

/**
 * Created by david on 16/11/24.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */

public interface IGlobalManager {
    /**
     * Exit the login state.
     */
    void logout();

    void tokenRefresh(LoginResponse response);
}
