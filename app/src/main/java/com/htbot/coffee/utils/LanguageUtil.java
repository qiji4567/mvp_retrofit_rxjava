package com.htbot.coffee.utils;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * @author 53443
 */
public class LanguageUtil {

    public static Context applyLanguage(Context context, String languageCode) {
        Locale locale = Locale.forLanguageTag(languageCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration(context.getResources().getConfiguration());
        config.setLocale(locale);
        config.setLayoutDirection(locale);

        // 返回带语言配置的新 context
        return context.createConfigurationContext(config);
    }

    public static void saveLanguageSetting(Context context, String languageCode) {
        context.getSharedPreferences("lang", Context.MODE_PRIVATE)
                .edit()
                .putString("language", languageCode)
                .apply();
    }

    public static String getSavedLanguage(Context context) {
        return context.getSharedPreferences("lang", Context.MODE_PRIVATE)
                .getString("language", "zh-CN");
    }
}
