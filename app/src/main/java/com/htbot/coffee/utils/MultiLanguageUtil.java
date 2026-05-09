package com.htbot.coffee.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;

import com.blankj.utilcode.util.SPUtils;

import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * 多语言切换的帮助类
 */
public class MultiLanguageUtil {

    private static final String TAG = "MultiLanguageUtil";

    private WeakReference<Context> mContext;
    private static final String SAVE_LANGUAGE = "save_language";

    private static class MultiLanguageUtilBinder {
        private final static MultiLanguageUtil util = new MultiLanguageUtil();
    }

    public static MultiLanguageUtil getInstance() {
        return MultiLanguageUtilBinder.util;
    }

    public void init(Context context) {
        mContext = new WeakReference<>(context);
    }

    private MultiLanguageUtil() {
    }

    /**
     * 设置语言
     */
    public void setConfiguration() {
        if (mContext != null) {
            Locale targetLocale = getLanguageLocale();
            Configuration configuration = mContext.get().getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(targetLocale);
            } else {
                configuration.locale = targetLocale;
            }
            Resources resources = mContext.get().getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            resources.updateConfiguration(configuration, dm);//语言更换生效的代码!
        }

    }

    //如果不是英文、简体中文、繁体中文，默认返回简体中文
    private Locale getLanguageLocale() {
        if (mContext == null || mContext.get() == null) return getSysLocale();
        int languageType = SPUtils.getInstance().getInt("languageType", 0);
        if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            return getSysLocale();
        } else if (languageType == LanguageType.LANGUAGE_EN) {
            return Locale.ENGLISH;
        } else if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            return Locale.SIMPLIFIED_CHINESE;
        } else if (languageType == LanguageType.LANGUAGE_KOREAN) {
            return Locale.KOREAN;
        }
        getSystemLanguage(getSysLocale());
        Log.e(TAG, "getLanguageLocale" + languageType + languageType);
        return Locale.SIMPLIFIED_CHINESE;
    }

    private String getSystemLanguage(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    //以上获取方式需要特殊处理一下
    public Locale getSysLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                LocaleListCompat listCompat = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration());
                locale = listCompat.get(0);
            } catch (Exception e) {
                locale = LocaleList.getDefault().get(0);
            }
        } else {
            locale = Locale.getDefault();

        }
        return locale;
    }

    /**
     * 更新语言
     *
     * @param languageType
     */
    public void updateLanguage(int languageType) {
        SPUtils.getInstance().put("languageType", languageType);
        MultiLanguageUtil.getInstance().setConfiguration();
    }



//    /**
//     * 获取到用户保存的语言类型
//     *
//     * @return
//     */
//    public int getLanguageType() {
//        int languageType = SharedPreferencesUtils.getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_FOLLOW_SYSTEM);
//        if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
//            return LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
//        } else if (languageType == LanguageType.LANGUAGE_KOREAN) {
//            return LanguageType.LANGUAGE_KOREAN;
//        } else if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
//            return LanguageType.LANGUAGE_FOLLOW_SYSTEM;
//        }
//        Log.e(TAG, "getLanguageType" + languageType);
//        return languageType;
//    }

    public Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context);
        } else {
            MultiLanguageUtil.getInstance().setConfiguration();
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getInstance().getLanguageLocale();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }
}
