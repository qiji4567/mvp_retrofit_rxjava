package cn.mrr.liubei.net;


import android.content.Intent;
import android.os.Looper;

import cn.mrr.liubei.R;
import cn.mrr.liubei.activity.ExitActivity;
import cn.mrr.liubei.manager.AppActivityTaskManager;
import cn.mvp.network.net.common.IdeaApiProxy;
import cn.mvp.network.net.module.LoginResponse;
import cn.mvp.network.net.token.IGlobalManager;
import cn.mvp.network.utils.ApplicationContextUtils;
import cn.mvp.network.utils.LogUtils;
import cn.mvp.network.utils.SPUtils;
import cn.mvp.network.utils.ToastUtils;


/**
 * Created by qiji on 2022/2/28.
 */

public class RetrofitHelper {

    private static IdeaApiService mIdeaApiService;

    public static IdeaApiService getApiService() {
        if (mIdeaApiService == null)
            mIdeaApiService = new IdeaApiProxy().getApiService(IdeaApiService.class,
                    ServerConfig.HOST, new IGlobalManager() {
                        @Override
                        public void logout() {
                            LogUtils.e("qqqqqqq  logout");
                            Looper.prepare();
                            ToastUtils.show(R.string.remote_login_logout);
                            Intent intent = new Intent();
                            intent.setClass(ApplicationContextUtils.getInstance(), ExitActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ApplicationContextUtils.getInstance().startActivity(intent);
                            AppActivityTaskManager.getInstance().removeAllActivity();
                            Looper.loop();
                        }

                        @Override
                        public void tokenRefresh(LoginResponse response) {
                            LogUtils.e("qqqqqqq  tokenRefresh " + response.toString());
//                            保存数据
                            SPUtils.saveObject(ApplicationContextUtils.getInstance(), response);
                        }
                    });
        return mIdeaApiService;
    }
}
