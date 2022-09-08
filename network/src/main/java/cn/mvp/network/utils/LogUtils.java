package cn.mvp.network.utils;

import android.text.TextUtils;
import android.util.Log;

import cn.mvp.network.BuildConfig;


/**
 * 日志打印
 * Created by xiarh on 2017/9/13.
 */

public class LogUtils {

    public static String tagPrefix = "dingDong";
    public static boolean showV = BuildConfig.LOG_DEBUG;
    public static boolean showD = BuildConfig.LOG_DEBUG;
    public static boolean showI = BuildConfig.LOG_DEBUG;
    public static boolean showW = BuildConfig.LOG_DEBUG;
    public static boolean showE = BuildConfig.LOG_DEBUG;
    public static boolean showWTF = BuildConfig.LOG_DEBUG;

    /**
     * 得到tag（所在类.方法（L:行））
     *
     * @return
     */
    private static String generateTag() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String callerClazzName = stackTraceElement.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String tag = "%s.%s(L:%d)";
        tag = String.format(tag, new Object[]{callerClazzName, stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber())});
        //给tag设置前缀
        tag = TextUtils.isEmpty(tagPrefix) ? tag : tagPrefix + ":" + tag;
        return tag;
    }

    public static void v(String msg) {
        if (showV) {
            String tag = generateTag();
            Log.v(tag, msg);
        }
    }

    public static void v(String msg, Throwable tr) {
        if (showV) {
            String tag = generateTag();
            Log.v(tag, msg, tr);
        }
    }

    public static void d(String msg) {
        if (showD) {
            String tag = generateTag();
            Log.d(tag, msg);
        }
    }

    public static void d(String msg, Throwable tr) {
        if (showD) {
            String tag = generateTag();
            Log.d(tag, msg, tr);
        }
    }

    public static void d(String tagPrefix, String msg) {
        if (showD) {
            String tag = generateTag();
            Log.e(tag, tagPrefix + " " + msg);
        }
    }

    public static void i(String msg) {
        if (showI) {
            String tag = generateTag();
            Log.i(tag, msg);
        }
    }

    public static void i(String msg, Throwable tr) {
        if (showI) {
            String tag = generateTag();
            Log.i(tag, msg, tr);
        }
    }

    public static void i(String tagPrefix, String msg) {
        if (showI) {
            String tag = generateTag();
            Log.i(tag, tagPrefix + " " + msg);
        }
    }

    public static void w(String msg) {
        if (showW) {
            String tag = generateTag();
            Log.w(tag, msg);
        }
    }

    public static void w(String msg, Throwable tr) {
        if (showW) {
            String tag = generateTag();
            Log.w(tag, msg, tr);
        }
    }

    public static void e(String msg) {
        if (showE) {
            String tag = generateTag();
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.e(tag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            //剩余部分
            Log.e(tag, msg);
        }
    }

    public static void e(String msg, Throwable tr) {
        if (showE) {
            String tag = generateTag();
            Log.e(tag, msg, tr);
        }
    }

    public static void e(String tagPrefix, String msg) {
        if (showE) {
            String tag = generateTag();
            Log.e(tag, tagPrefix + " " + msg);
        }
    }

    public static void wtf(String msg) {
        if (showWTF) {
            String tag = generateTag();
            Log.wtf(tag, msg);
        }
    }

    public static void wtf(String msg, Throwable tr) {
        if (showWTF) {
            String tag = generateTag();
            Log.wtf(tag, msg, tr);
        }
    }
}
