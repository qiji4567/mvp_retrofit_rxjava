package com.htbot.coffee.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ButtonUtils {
    private static long lastClickTime = 0;
    private static long DIFF = 1000;
    private static int lastButtonId = -1;

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于设置时间time，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(long time) {
        return isFastDoubleClick(-1, time);
    }


    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }

    public static boolean isPassword(String password) {
        // 正则表达式：数字和字母组合，大小写字母都可以
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        boolean isMatch = m.matches();
        return isMatch;
    }

    public static boolean isEmial(String password) {
        String regex = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        boolean isMatch = m.matches();
        System.out.println(isMatch);
        return isMatch;
    }

//
//    public static void saveImageToGallery(Context context, Bitmap bmp)
//    {
//        // 首先保存图片
//        File appDir = new File(Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "xinagwang");
//        if (!appDir.exists())
//        {
//            Log.e("d", "去创建文件夹");
//            appDir.mkdirs();
//        }
//        String fileName = "djgx_" + System.currentTimeMillis() + ".jpg";
//        File file = new File(appDir, fileName);
//        try
//        {
//            Log.e("d", "开始保存图片");
//            FileOutputStream fos = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//            fos.close();
//            // 最后通知图库更新
//            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
//            ToastUtils.showLong("图片保存成功");
//
//        } catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//            Log.e("d", "保存图片失败:" + e.toString());
//            ToastUtils.showLong("图片保存失败");
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//            Log.e("d", "保存图片失败:" + e.toString());
//            ToastUtils.showLong("图片保存失败");
//        }
//    }
//

    public static String getStarMobile(String mobile) {

        if (!TextUtils.isEmpty(mobile)) {

            if (mobile.length() >= 11)

                return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());

        } else {

            return "";

        }

        return mobile;

    }

}
