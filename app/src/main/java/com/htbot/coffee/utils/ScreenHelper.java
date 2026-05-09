package com.htbot.coffee.utils;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;


/**
 * Screen Helper Class to get default dpi .
 */
public class ScreenHelper {
    private Context context;

    public ScreenHelper(Context context) {
        this.context = context;
    }

    /**
     * Dcm Log Tag .
     */
    private static final String TAG = "ScreenHelper";

    /**
     * The defalut LDPI value .
     */
    private static final int LDPI = DisplayMetrics.DENSITY_DEFAULT;
    /**
     * The defalut HDPI value .
     */
    private static final int HDPI = DisplayMetrics.DENSITY_HIGH;
    /**
     * The defalut XHDPI value .
     */
    private static final int XHDPI = DisplayMetrics.DENSITY_XHIGH;
    /**
     * The defalut XXHDPI value .
     */
    private static final int XXHDPI = DisplayMetrics.DENSITY_XXHIGH;
    /**
     * The 560 dpi value .
     */
    private static final int DPI_560 = DisplayMetrics.DENSITY_560;

    /**
     * The defalut XXXHDPI value .
     */
    private static final int XXXHDPI = DisplayMetrics.DENSITY_XXXHIGH;

    /**
     * The standard 720p device width.
     */
    private static final int DEFALUT_SCREEN_WIDTH = 720;
    /**
     * The standard 1080p device width.
     */
    private static final int MEDIUM_SCREEN_WIDTH = 1080;
    /**
     * The standard 4k device width.
     */
    private static final int HIGH_SCREEN_WIDTH = 1440;
    /**
     * The standard max device width.
     */
    private static final int MAX_SCREEN_WIDTH = 2160;

    /**
     * The Max Screen DP to distinguish phone and tablet.
     */
    private static final int MAX_SCREEN_DP = 600;
    /**
     * Default Mode Name .
     */
    private static final String DEFAULT_MODE = "defaultMode";

    /**
     * getDefaultDpi.<br>
     * Note : get default dpi when Display Size has changed
     *
     * @param context attach activity context
     * @return default dpi
     */
    public int getDefaultDpi(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);

//        boolean changed = isChangedResolution(display, metrics);
        boolean changed = false;
        // if it is tablet,no need to keep current dpi
//        if (isTablet(context)) {
//            return metrics.densityDpi;
//        } else if (changed) {
//            // get special device dpi
//            return getSpecialDeviceDpi(metrics);
//        } else {
//            return getInitialDisplayDensity(metrics);
//        }
        return getInitialDisplayDensity(metrics);
    }


    private int getInitialDisplayDensity(DisplayMetrics metrics) {
        int physicalDensity = metrics.densityDpi; // 默认值为当前设备的屏幕密度
        try {
            // 获取屏幕显示密度，通常直接使用 DisplayMetrics 即可
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            physicalDensity = displayMetrics.densityDpi;
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常时使用原始密度
        }

        return physicalDensity;
    }


    /**
     * isChangedResolution.<br>
     * Note : check if huawei device
     *
     * @param display view display instance
     * @param displayMetrics view displayMetrics to get some device screen attr
     * @return true is huawei device,false is not
     */
//    private boolean isChangedResolution(Display display, DisplayMetrics displayMetrics) {
//        Display.Mode[] modes = display.getSupportedModes();
//        if (modes.length > 1) {
//            String displayInfo = display.toString();
//
//            int firstIndex = displayInfo.indexOf(DEFAULT_MODE);
//            int length = DEFAULT_MODE.length();
//            String defaultModeInfo = displayInfo.substring(firstIndex);
//            int firstSymbolIndex = defaultModeInfo.indexOf(",");
//            String defaultMode = defaultModeInfo.substring(length, firstSymbolIndex);
//            int defaultModeIndex = Integer.parseInt(defaultMode.replace(" ", ""));
//            for (Display.Mode mode : modes) {
//                if (mode.getModeId() == defaultModeIndex) {
//                    int defaultWidth = mode.getPhysicalWidth();
//                    return defaultWidth != displayMetrics.widthPixels;
//                }
//            }
//        }
//
//        // get the init screen Pixels
//        int physicalWidth = display.getMode().getPhysicalWidth();
//        int physicalHeight = display.getMode().getPhysicalHeight();
//
//        // get current screen Pixels
//        int currentWidth = displayMetrics.widthPixels;
//        int currentHeight = displayMetrics.heightPixels;
//
//        //if init size is different with current size, this means resolution
//        //has changed
//        if (physicalWidth != currentWidth && physicalHeight != currentHeight) {
//            return true;
//        }
//
//        return false;
//    }

    /**
     * getSpecialDeviceDpi.<br>
     * Note : when the Resolution could be changed, according to stardand Resolution to
     * get the dpi
     *
     * @param displayMetrics display metrics instances
     * @return standard dpi
     */
    private int getSpecialDeviceDpi(DisplayMetrics displayMetrics) {
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;

        // get min Pixels.
        int minPixels = Math.min(widthPixels, heightPixels);


        // get the apposite dpi
        if (minPixels < DEFALUT_SCREEN_WIDTH) {
            return HDPI;
        } else if (minPixels >= DEFALUT_SCREEN_WIDTH && minPixels < MEDIUM_SCREEN_WIDTH) {
            return XHDPI;
        } else if (minPixels >= MEDIUM_SCREEN_WIDTH && minPixels < HIGH_SCREEN_WIDTH) {
            return XXHDPI;
        } else if (minPixels >= HIGH_SCREEN_WIDTH && minPixels < MAX_SCREEN_WIDTH) {
            return DPI_560;
        } else {
            return XXXHDPI;
        }
    }

    /**
     * isTablet.<br>
     * Note : check if current device is tablet.
     *
     * @param context attach activity context
     * @return true is tablet, false is not
     */
//    private boolean isTablet(Context context) {
//        return context.getResources().getBoolean(R.bool.isTablet);
//    }


    /**
     * get default resolution beacause HuaWei can setting multi resolution,
     * this method can get the default when you change to another resolution
     *
     * @param context
     * @return
     */
    public int getDefaultResolutionWidth(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        Display.Mode[] modes = display.getSupportedModes();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);

        if (modes.length > 1) {
            String displayInfo = display.toString();

            int firstIndex = displayInfo.indexOf(DEFAULT_MODE);
            int length = DEFAULT_MODE.length();
            String defaultModeInfo = displayInfo.substring(firstIndex);
            int firstSymbolIndex = defaultModeInfo.indexOf(",");
            String defaultMode = defaultModeInfo.substring(length, firstSymbolIndex);
            int defaultModeIndex = Integer.parseInt(defaultMode.replace(" ", ""));
            for (Display.Mode mode : modes) {
                if (mode.getModeId() == defaultModeIndex) {
                    int defaultWidth = mode.getPhysicalWidth();
                    return defaultWidth;
                }
            }
        }

        // get the init screen Pixels
        int physicalWidth = display.getMode().getPhysicalWidth();
        int physicalHeight = display.getMode().getPhysicalHeight();


        return physicalWidth;
    }
}