package com.htbot.coffee.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Gson 工具类（业务友好型）
 */
public final class GsonUtils {
    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final TimeZone TIMEZONE = TimeZone.getTimeZone("GMT+8");

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                    // 如果是整数，去掉 .0
                    if (src == null) return JsonNull.INSTANCE;
                    if (src == src.longValue()) {
                        return new JsonPrimitive(src.longValue());
                    }
                    return new JsonPrimitive(src);
                }
            })
            .registerTypeAdapter(Float.class, new JsonSerializer<Float>() {
                @Override
                public JsonElement serialize(Float src, Type typeOfSrc, JsonSerializationContext context) {
                    if (src == null) return JsonNull.INSTANCE;
                    if (src == src.intValue()) {
                        return new JsonPrimitive(src.intValue());
                    }
                    return new JsonPrimitive(src);
                }
            })
            .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                @Override
                public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext ctx) {
                    if (src == null) {
                        return JsonNull.INSTANCE;
                    }
                    SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
                    format.setTimeZone(TIMEZONE);
                    return new JsonPrimitive(format.format(src));
                }
            })
            .disableHtmlEscaping()
            .create();

    private GsonUtils() {
    }

    /* ===================== 基础 ===================== */

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) return null;
        return GSON.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type type) {
        if (TextUtils.isEmpty(json)) return null;
        return GSON.fromJson(json, type);
    }

    public static String toJson(Object obj) {
        return obj == null ? "" : GSON.toJson(obj);
    }

    public static String prettyJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return "";
        }
        try {
            JsonElement element = JsonParser.parseString(json);
            return new GsonBuilder().setPrettyPrinting().create().toJson(element);
        } catch (Throwable ignore) {
            // 不是合法 JSON 或解析失败，原样返回
            return json;
        }
    }
    /* ===================== List ===================== */

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return GSON.fromJson(json, type);
    }




    /* ===================== 判空辅助 ===================== */


    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}
