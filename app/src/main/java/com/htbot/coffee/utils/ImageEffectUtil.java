package com.htbot.coffee.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.AsyncTask;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class ImageEffectUtil {
    public static void applyEffectsAsync(Context context, Bitmap src, float blurRadius, int alpha, int r, int g, int b, ImageEffectCallback callback) {
        new ApplyEffectsTask(context, src, blurRadius, alpha, r, g, b, callback).execute();
    }

    private static class ApplyEffectsTask extends AsyncTask<Void, Void, Bitmap> {
        private Context context;
        private Bitmap src;
        private float blurRadius;
        private int alpha, r, g, b;
        private ImageEffectCallback callback;

        public ApplyEffectsTask(Context context, Bitmap src, float blurRadius, int alpha, int r, int g, int b, ImageEffectCallback callback) {
            this.context = context;
            this.src = src;
            this.blurRadius = blurRadius;
            this.alpha = alpha;
            this.r = r;
            this.g = g;
            this.b = b;
            this.callback = callback;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            // 1. 创建可变副本
            Bitmap mutableBitmap = src.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap blurred = mutableBitmap;

            // 2. 应用模糊
//            for (int i = 0; i < 5; i++) {
//                blurred = blur(context, blurred, blurRadius);
//            }
            blurred = FastBlurUtil.toBlur(blurred, 10);
            // 3. 应用颜色和透明度
            Paint paint = new Paint();
            paint.setColor(Color.argb(alpha, r, g, b));
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

            Canvas canvas = new Canvas(blurred);
            canvas.drawRect(0, 0, blurred.getWidth(), blurred.getHeight(), paint);

            return blurred;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Pass the result back to the callback
            if (callback != null) {
                callback.onEffectApplied(result);
            }
        }
    }

    // Callback interface for result handling
    public interface ImageEffectCallback {
        void onEffectApplied(Bitmap result);
    }

    private static Bitmap blur(Context context, Bitmap image, float radius) {
        // 确保处理的是可变Bitmap
        Bitmap input = image.isMutable() ? image : image.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap output = Bitmap.createBitmap(input.getWidth(), input.getHeight(), Bitmap.Config.ARGB_8888);

        // 创建 RenderScript
        RenderScript rs = RenderScript.create(context);

        // 创建高斯模糊脚本
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 创建 Allocation 对象用于输入和输出
        Allocation tmpIn = Allocation.createFromBitmap(rs, input);
        Allocation tmpOut = Allocation.createFromBitmap(rs, output);

        // 设置高斯模糊的半径（值越大，模糊效果越明显）
        script.setRadius(radius);

        // 设置输入并执行模糊操作
        script.setInput(tmpIn);
        script.forEach(tmpOut);

        // 将结果复制回 output Bitmap
        tmpOut.copyTo(output);

        // 清理资源
        rs.destroy();

        return output;
    }
}
