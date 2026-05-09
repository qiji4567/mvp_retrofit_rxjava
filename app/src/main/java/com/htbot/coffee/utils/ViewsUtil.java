package com.htbot.coffee.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;

import com.blankj.utilcode.util.StringUtils;


/**
 * ScreenUtils
 * <ul>
 * <strong>Convert between dp and sp</strong>
 * </ul>
 */
public class ViewsUtil {

    private ViewsUtil() {
        throw new AssertionError();
    }

    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float pxToDp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static int dpToPxInt(Context context, float dp) {
        return (int) (dpToPx(context, dp) + 0.5f);
    }

    public static int pxToDpCeilInt(Context context, float px) {
        return (int) (pxToDp(context, px) + 0.5f);
    }


    /**
     * px转换为sp
     *
     * @param context 上下文环境
     * @param pxValue 需要转换的值
     * @return int
     **/
    public static int pxToSp(Context context, int pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp转换为px
     *
     * @param context 上下文环境
     * @param spValue 需要转换的值
     * @return int
     **/
    public static int spToPx(Context context, int spValue) {
        final float density = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * density + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }

    /**
     * 获得状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


    /**
     * 代码设置shape
     *
     * @param _strokeWidth 沿边线厚度；无需传入-1
     * @param _roundRadius 圆角半径；无需传入-1
     * @param _shape       shape绘制类型(rectangle、oval等)；无需传入-1，将采用默认的GradientDrawable.RECTANGLE
     * @param _strokeColor 沿边线颜色；无需传入null/""
     * @param _fillColor   内部填充颜色
     * @return
     */

    public static GradientDrawable createShape(Context context, int _strokeWidth,
                                               int _roundRadius, int _shape,
                                               String _strokeColor, String _fillColor) {
        int strokeWidth = dpToPxInt(context, _strokeWidth); // px not dp
        int roundRadius = dpToPxInt(context, _roundRadius);// px not dp
        int strokeColor = -1;
        if (null != _strokeColor && !_strokeColor.equals("")) {
            strokeColor = getHexColor(_strokeColor);
        }

        GradientDrawable gd = new GradientDrawable();
        if (!StringUtils.isEmpty(_fillColor)) {
            int fillColor = getHexColor(_fillColor);
            gd.setColor(fillColor);
        }
        if (-1 == _shape) {
            gd.setShape(GradientDrawable.RECTANGLE);
        } else {
            gd.setShape(_shape);
        }
        if (-1 != roundRadius) {
            gd.setCornerRadius(roundRadius);
        }
        if (-1 != strokeWidth && -1 != strokeColor) {
            gd.setStroke(strokeWidth, strokeColor);
        }
        return gd;
    }


    /**
     * 处理#相关问题颜色（没#添加#）
     *
     * @param colorStr
     * @return
     */
    public static int getHexColor(String colorStr) {
        try {
            if (!StringUtils.isEmpty(colorStr)) {
                int color;
                if (!colorStr.contains("#")) {
                    color = Color.parseColor("#" + colorStr);
                } else {
                    color = Color.parseColor(colorStr);
                }
                return color;
            }
        } catch (Exception e) {
        }
        return Color.WHITE;
    }
}
