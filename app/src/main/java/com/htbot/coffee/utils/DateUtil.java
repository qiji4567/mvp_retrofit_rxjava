package com.htbot.coffee.utils;

import android.icu.util.Calendar;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 王鑫 on 2019/11/20.
 * 时间转换工具
 */

public class DateUtil {


    /**
     * 时间戳转(月-日)
     *
     * @param time
     * @return
     */
    public static String dateToMMDD(long time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 时间戳转(月.日 时:分)
     *
     * @param time
     * @return
     */
    public static String dateToMMDDHHmm(long time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("MM.dd HH:mm");
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 时间戳转(年-月-日)
     *
     * @param time
     * @return
     */
    public static String dateToYYYYMMDD(long time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 时间戳转(年-月-日 时)
     *
     * @param time
     * @return
     */
    public static String dateToYYYYMMDDHH(long time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 时间戳转(年-月-日 时:分)
     *
     * @param time
     * @return
     */
    public static String dateToYYYYMMDDHHmm(long time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 时间戳转(年-月-日 时:分)
     * <p>
     * param time
     *
     * @return
     */
    public static String dateToYYYYMMDDHHmm(Date date) {
        try {
//            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 时间戳转(年-月-日 时:分)
     * <p>
     * param time
     *
     * @return
     */
    public static String dateToYYYYMMDDHHMMss(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 时间戳转(年-月-日 时:分:00)
     * <p>
     * param time
     *
     * @return
     */
    public static String dateToYYYYMMDDHH00(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            String formattedTime = sdf.format(date);
            return formattedTime + ":00:00";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 时间戳转(年-月-日 时:分:秒)
     *
     * @param time
     * @return
     */
    public static String dateToAllTime(long time) {
        if (0 == time) {
            return "";
        }
        try {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 时间戳转(时:分:秒)
     *
     * @param time
     * @return
     */
    public static String dateToHHmmss(long time) {
        if (0 == time) {
            return "";
        }
        try {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 时间戳转(时:分)
     *
     * @param time
     * @return
     */
    public static String dateToHHmm(long time) {
        if (0 == time) {
            return "";
        }
        try {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 时间转时间戳
     * <p>
     * param time (月-日-时-分)
     *
     * @return
     */
    public static String MMDDHHmmToString(String dateTimeString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM-dd HH:mm");

        try {
            Date dateTime = inputFormat.parse(dateTimeString);
            String result = outputFormat.format(dateTime);
//            System.out.println("转换后的字符串: " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 时间转时间戳
     * <p>
     * param time (年-月-日-时-分)
     *
     * @return
     */
    public static String YYYYMMDDHHmmToString(String dateTimeString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date dateTime = inputFormat.parse(dateTimeString);
            String result = outputFormat.format(dateTime);
//            System.out.println("转换后的字符串: " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 时间转时间戳
     * <p>
     * param time (年-月-日)
     *
     * @return
     */
    public static String HHmmToString(String dateTimeString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");

        try {
            Date dateTime = inputFormat.parse(dateTimeString);
            String result = outputFormat.format(dateTime);
//            System.out.println("转换后的字符串: " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    //----------------------------------时间转时间戳---------------------------------------------------------------------------

    /**
     * 时间转时间戳
     *
     * @param time (年-月-日 时:分:秒)
     * @return
     */
    public static long yyyyMMDDHHmmssToDate(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(time).getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 时间转时间戳
     *
     * @param time (年-月-日 时:分)
     * @return
     */
    public static long yyyyMMDDHHmmToDate(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.parse(time).getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 时间转时间戳
     *
     * @param time (年-月-日)
     * @return
     */
    public static long yyyyMMDDToDate(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(time).getTime();
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }


    /**
     * 日期判断，是否增加一天或者减少一天
     *
     * @param onTime   当前时间 为了取年月日
     * @param workTime 工作时间 为了取时分
     * @param type     判断状态 -1减少一天   1增加一天   0当天
     * @return
     */
    public static long workLimit(long onTime, String workTime, int type) {
        long time = onTime;
        workTime = dateToYYYYMMDD(time) + " " + workTime;
        time = yyyyMMDDHHmmToDate(workTime);
        Date date = null;
        if (0 == type) {
            return time;
        }
        if (-1 == type) {
            date = addAndSubtractDaysByGetTime(new Date(time), -1);
        } else if (1 == type) {
            date = addAndSubtractDaysByGetTime(new Date(time), 1);
        }
        return date.getTime();
    }

    /**
     * 日期判断，是否增加一天或者减少一天
     *
     * @param onTime 当前时间
     * @param type   判断状态 -1减少一天   1增加一天   0当天
     * @return
     */
    public static long workLimit(long onTime, int type) {
        long time = onTime;
        Date date = null;
        if (0 == type) {
            return time;
        }
        if (-1 == type) {
            date = addAndSubtractDaysByGetTime(new Date(time), -1);
        } else if (1 == type) {
            date = addAndSubtractDaysByGetTime(new Date(time), 1);
        }
        return date.getTime();
    }


    private static Date addAndSubtractDaysByGetTime(Date dateTime/*待处理的日期*/, int n/*加减天数*/) {
        //日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(dd.format(new Date(dateTime.getTime() + n * 24 * 60 * 60 * 1000L)));
        //注意这里一定要转换成Long类型，要不n超过25时会出现范围溢出，从而得不到想要的日期值
        return new Date(dateTime.getTime() + n * 24 * 60 * 60 * 1000L);
    }


    /**
     * 计算当前时间相差小时
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long calculateHourDifference(Date startTime, Date endTime) {
        // 创建 Calendar 对象来计算时间差
        java.util.Calendar startCal = java.util.Calendar.getInstance();
        startCal.setTime(startTime);

        java.util.Calendar endCal = java.util.Calendar.getInstance();
        endCal.setTime(endTime);

        // 计算时间差
        long millisecondsDifference = endCal.getTimeInMillis() - startCal.getTimeInMillis();
        long hoursDifference = millisecondsDifference / (60 * 60 * 1000);

        return hoursDifference;
    }


    /**
     * 判断给定的年、月、日是否在今天之后
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean isAfterToday(int year, int month, int day) {
        // 获取当前日期
        Calendar today = Calendar.getInstance();

        // 设置要比较的日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day); // 注意月份需要减去1，因为Calendar的月份从0开始

        // 比较日期
        return calendar.after(today) || isSameDay(calendar, today);
    }

    private static boolean isSameDay(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @return true=上午 false=下午
     */
    public static boolean getTimeOfDay() {
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        // 使用 SimpleDateFormat 格式化时间，获取小时部分
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        String hour = dateFormat.format(currentTime);

        // 将小时部分转换为整数
        int hourInt = Integer.parseInt(hour);

        // 判断是上午还是下午
        return (hourInt >= 0 && hourInt < 12);
    }


    public static String getTodayStartTime() {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();

        // 清除时、分、秒、毫秒
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 获取当天的开始时间
        return dateToAllTime(calendar.getTimeInMillis());
    }

    public static String getTodayEndTime() {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();

        // 设置时、分、秒、毫秒为最后的时间
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        // 获取当天的结束时间
        return dateToAllTime(calendar.getTimeInMillis());
    }

    public static String yearMonthDayStartTime(int year, int month, int day) {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0); // 月份从0开始
        calendar.set(Calendar.MILLISECOND, 0);

        // 获取当天的开始时间
        return dateToAllTime(calendar.getTimeInMillis());
    }

    public static String yearMonthDayEndTime(int year, int month, int day) {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, day, 23, 59, 59); // 月份从0开始
        calendar.set(Calendar.MILLISECOND, 999);

        // 获取当天的结束时间
        return dateToAllTime(calendar.getTimeInMillis());
    }

    /**
     * 返回指定年月的月初和月末日期
     * param year
     * param month
     * return
     */
    public static Map<String, Integer> getMonthStartAndEnd(int year, int month) {
        Map<String, Integer> result = new HashMap<>();

        // 获取当前日期时间对应的 Calendar 实例
        Calendar calendar = Calendar.getInstance();

        // 设置 Calendar 对象的年月日时分秒
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);  // Java 中的月份从 0 开始计数，因此要减 1
        calendar.set(Calendar.DATE, 1);  // 将日期设置为该月的第一天

        // 获取该月的第一天是星期几
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // 计算该月的天数
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 计算月初和月末的日期
        int monthStart = 1;
        int monthEnd = daysInMonth;

        // 将月初和月末的日期存储到结果集中
        result.put("start", monthStart);
        result.put("end", monthEnd);

        return result;
    }

    /**
     * 计算 开始时间-结束时间
     *
     * @param startTime
     * @param endTime
     * @return 返回 时 分
     */
    public static String calculateTimeDifference(String startTime, String endTime) {

        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return "";
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate = format.parse(startTime);
            Date endDate = format.parse(endTime);

            long durationInMillis = endDate.getTime() - startDate.getTime();

            // 计算小时数和分钟数
            long hours = (durationInMillis / (1000 * 60 * 60)) % 24;
            long minutes = (durationInMillis / (1000 * 60)) % 60;

            return hours + "小时 " + minutes + "分钟";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 计算时间 小时
     * <p>
     * param startTime
     * param endTime
     *
     * @return
     */
    public static long calculateHourTime(Date startDate, Date endDate) {

        if (startDate == null || endDate == null) {
            return 0;
        }

        try {
            long durationInMillis = endDate.getTime() - startDate.getTime();
            // 计算小时数和分钟数
            long hours = (durationInMillis / (1000 * 60 * 60)) % 24;
            return hours;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    public static boolean isEndTimeBeforeStartTime(Date startTimeDate, Date endTimeDate) {
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

        try {
//            Date startTimeDate = format.parse(startTime);
//            Date endTimeDate = format.parse(endTime);

            return endTimeDate.before(startTimeDate);
        } catch (Exception e) {
            e.printStackTrace();
            // 处理解析异常的逻辑，例如返回默认结果或抛出异常
        }

        return false;
    }


    //时间天数差----------------------------------------------------------------------------

    /**
     * 获取最近 day 天的开始时间
     *
     * @param day 最近几天，包含今天
     *            1 = 今天 00:00:00
     *            3 = 前天 00:00:00
     *            7 = 6 天前 00:00:00
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getBeforeDayStartTime(int day) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        int offsetDay = Math.max(day, 1) - 1;
        calendar.add(java.util.Calendar.DAY_OF_MONTH, -offsetDay);

        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calendar.set(java.util.Calendar.MINUTE, 0);
        calendar.set(java.util.Calendar.SECOND, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);

        return dateToAllTime(calendar.getTimeInMillis());
    }

}
