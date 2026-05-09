package com.htbot.coffee.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.htbot.coffee.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * OkHttp 风格日志：带框、箭头、JSON 美化、超长分段、可落盘。
 */
public final class LogUtils {

    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final int MAX_LENGTH = 3500;

    private static boolean enableFileLog = false;

    private static String LOG_DIR;

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static final String TOP_BORDER =
            "┌────────────────────────────────────────────────────────";
    private static final String MIDDLE_BORDER = "│ ";
    private static final String BOTTOM_BORDER =
            "└────────────────────────────────────────────────────────";

    private static final String REQ_PREFIX = "--> ";
    private static final String RESP_PREFIX = "<-- ";

    private static final String TAG = "Coffee";

    private LogUtils() {
    }

    public static void init(String baseDir) {
        if (TextUtils.isEmpty(baseDir)) {
            Log.e("LogUtils", "Base directory is null, logs may not be saved");
            return;
        }

        LOG_DIR = baseDir + "/logs/";

        File dir = new File(LOG_DIR);
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }
    }

    public static void initWithInternalStorage(Context context) {
        if (context == null) {
            return;
        }

        try {
            File logDir = new File(context.getFilesDir(), "logs");
            if (!logDir.exists()) {
                //noinspection ResultOfMethodCallIgnored
                logDir.mkdirs();
            }

            LOG_DIR = logDir.getAbsolutePath() + "/";
        } catch (Exception e) {
            Log.e("LogUtils", "Failed to initialize: " + e.getMessage());
        }
    }

    public static void d(String tag, String msg) {
        d(tag + " ------> " + msg);
    }

    public static void d(String msg) {
        log(Log.DEBUG, msg);
    }

    public static void i(String tag, String msg) {
        i(tag + " ------> " + msg);
    }

    public static void i(String msg) {
        log(Log.INFO, msg);
    }

    public static void w(String tag, String msg) {
        w(tag + " ------> " + msg);
    }

    public static void w(String msg) {
        log(Log.WARN, msg);
    }

    public static void e(String tag, String msg) {
        e(tag + " ------> " + msg);
    }

    public static void e(String msg) {
        log(Log.ERROR, msg);
    }

    public static void json(String msg) {
        log(Log.DEBUG, prettyJson(msg));
    }

    public static void httpRequest(String method, String url, String headers, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(REQ_PREFIX).append(safe(method)).append(" ").append(safe(url)).append("\n");

        if (!TextUtils.isEmpty(headers)) {
            sb.append("Headers:\n").append(headers.trim()).append("\n");
        }

        if (!TextUtils.isEmpty(body)) {
            sb.append("Body:\n").append(prettyJson(body)).append("\n");
        }

        sb.append(REQ_PREFIX).append("END ").append(safe(method));
        log(Log.DEBUG, sb.toString());
    }

    public static void httpResponse(String url, int code, long costMs, String headers, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(RESP_PREFIX)
                .append(code)
                .append(" ")
                .append(safe(url))
                .append(" (")
                .append(costMs)
                .append("ms)")
                .append("\n");

        if (!TextUtils.isEmpty(headers)) {
            sb.append("Headers:\n").append(headers.trim()).append("\n");
        }

        if (!TextUtils.isEmpty(body)) {
            sb.append("Body:\n").append(prettyJson(body)).append("\n");
        }

        sb.append(RESP_PREFIX).append("END HTTP");
        log(Log.DEBUG, sb.toString());
    }

    private static void log(int level, String msg) {
        Caller caller = getCaller();
        String time = now();
        String body = msg == null ? "null" : msg;

        if (DEBUG) {
            printBoxed(level, caller, time, body);
        }

        if (enableFileLog) {
            writeToFile(level, caller.shortLocation(), time + " → " + body);
        }
    }

    private static void printBoxed(int level, Caller caller, String time, String body) {
        Log.println(level, TAG, TOP_BORDER);
        Log.println(level, TAG, MIDDLE_BORDER + caller.fullLocation());
        Log.println(level, TAG, MIDDLE_BORDER + time);
        Log.println(level, TAG, MIDDLE_BORDER + "at " + caller.className + "." + caller.methodName
                + "(" + caller.fileName + ":" + caller.lineNumber + ")");
        Log.println(level, TAG, MIDDLE_BORDER);

        if (!TextUtils.isEmpty(body)) {
            String[] lines = body.split("\n");
            for (String line : lines) {
                printChunk(level, TAG, MIDDLE_BORDER + line);
            }
        } else {
            Log.println(level, TAG, MIDDLE_BORDER + " ");
        }

        Log.println(level, TAG, BOTTOM_BORDER);
    }

    private static void printChunk(int level, String tag, String line) {
        if (line == null) {
            Log.println(level, tag, "null");
            return;
        }

        if (line.length() <= MAX_LENGTH) {
            Log.println(level, tag, line);
            return;
        }

        int start = 0;
        while (start < line.length()) {
            int end = Math.min(start + MAX_LENGTH, line.length());
            Log.println(level, tag, line.substring(start, end));
            start = end;
        }
    }

    private static Caller getCaller() {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        if (stack == null) {
            return Caller.unknown();
        }

        for (StackTraceElement e : stack) {
            if (e == null) {
                continue;
            }

            String cls = e.getClassName();
            if (cls == null) {
                continue;
            }

            if (cls.equals(LogUtils.class.getName())) {
                continue;
            }

            if (cls.startsWith("dalvik.system.")) {
                continue;
            }

            if (cls.startsWith("java.lang.Thread")) {
                continue;
            }

            if (cls.startsWith("android.util.Log")) {
                continue;
            }

            return new Caller(e);
        }

        return Caller.unknown();
    }

    private static class Caller {
        final String className;
        final String methodName;
        final String fileName;
        final int lineNumber;

        Caller(StackTraceElement e) {
            this.className = e.getClassName() == null ? "Unknown" : e.getClassName();
            this.methodName = e.getMethodName() == null ? "unknown" : e.getMethodName();
            this.fileName = e.getFileName() == null ? "Unknown.java" : e.getFileName();
            this.lineNumber = e.getLineNumber();
        }

        static Caller unknown() {
            return new Caller(new StackTraceElement("Unknown", "unknown", "Unknown.java", -1));
        }

        String fullLocation() {
            return className + "." + methodName + "(" + fileName + ":" + lineNumber + ")"
                    + " [" + Thread.currentThread().getName() + "]";
        }

        String shortLocation() {
            String file = fileName.replace(".java", "");
            return file + ":" + lineNumber + " [" + Thread.currentThread().getName() + "]";
        }
    }

    private static String prettyJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return json;
        }

        try {
            String trim = json.trim();
            if (trim.startsWith("{")) {
                return new JSONObject(trim).toString(4);
            }
            if (trim.startsWith("[")) {
                return new JSONArray(trim).toString(4);
            }
        } catch (JSONException ignored) {
        }

        return json;
    }

    private static void writeToFile(int level, String tag, String msg) {
        if (TextUtils.isEmpty(LOG_DIR)) {
            if (DEBUG) {
                Log.w(TAG, "Log directory not initialized, skipping file write");
            }
            return;
        }

        String filePath = LOG_DIR + getToday() + ".log";

        try (FileWriter writer = new FileWriter(filePath, true)) {
            String log = "[" + levelToStr(level) + "] " + tag + ": " + msg + "\n";
            writer.write(log);
        } catch (IOException e) {
            Log.e(TAG, "写入日志失败：" + e.getMessage() + ", path: " + filePath);
        }
    }

    private static String levelToStr(int level) {
        switch (level) {
            case Log.DEBUG:
                return "DEBUG";
            case Log.INFO:
                return "INFO";
            case Log.WARN:
                return "WARN";
            case Log.ERROR:
                return "ERROR";
            default:
                return "LOG";
        }
    }

    @SuppressLint("SimpleDateFormat")
    private static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    private static String now() {
        return DATE_FORMAT.format(new Date());
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

    public static void initCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) ->
                log(Log.ERROR, Log.getStackTraceString(e))
        );
    }

    public static void setEnableFileLog(boolean enable) {
        enableFileLog = enable;
    }
}