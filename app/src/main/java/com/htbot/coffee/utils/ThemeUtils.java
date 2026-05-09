package com.htbot.coffee.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.htbot.coffee.R;

/**
 * @author 53443
 */
public class ThemeUtils {
    public static int[] themeImage = {R.mipmap.ic_main_bg, R.mipmap.ic_theme_2, R.mipmap.ic_theme_3, R.mipmap.ic_theme_4};

    public static void setThemeBg(View view, int p) {
        view.setBackgroundResource(themeImage[p]);
    }

    public static int getThemeImage() {
        int type = SPUtils.getInstance().getInt("themeType", 0);
        return themeImage[type];
    }

    public static Bitmap themeBitmap = null;

    public static void setTheme(Context context, ImageView iv) {
        Bitmap original = BitmapFactory.decodeResource(context.getResources(), ThemeUtils.getThemeImage());
        int r = SPUtils.getInstance().getInt("color1", 0);
        int g = SPUtils.getInstance().getInt("color2", 0);
        int b = SPUtils.getInstance().getInt("color3", 0);
        int a = SPUtils.getInstance().getInt("color4", 100);
        if (r == 0 && g == 0 && b == 0) {
            a = 0;
        }
        if (themeBitmap == null) {
            ImageEffectUtil.applyEffectsAsync(context, original, 25f, (int) (255 * (a / 100f)),
                    (int) (255 * (r / 100f)), (int) (255 * (g / 100f)), (int) (255 * (b / 100f)), new ImageEffectUtil.ImageEffectCallback() {
                        @Override
                        public void onEffectApplied(Bitmap result) {
                            themeBitmap = result;
                            iv.setImageBitmap(result);
                        }
                    });
        } else {
            iv.setImageBitmap(themeBitmap);
        }


    }
}
