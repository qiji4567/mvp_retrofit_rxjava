package cn.mvp.network.utils;

import android.content.Context;

/**
 * <pre>
 *     author: qiji
 *     time  : 2022/2/28
 *     desc  : Utils初始化相关
 * </pre>
 */
public class ApplicationContextUtils {

    private static Context context;

    private ApplicationContextUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        ApplicationContextUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getInstance() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }


}