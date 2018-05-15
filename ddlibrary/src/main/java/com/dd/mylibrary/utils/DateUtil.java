package com.dd.mylibrary.utils;



import com.dd.mylibrary.bean.DateBean;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by yanghongyu on 2017/12/18.
 * 日期工具类
 */

public class DateUtil {

    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DEFAULT_SDF1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat TIME_MILLION = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat TIME_SECCOND_MILLION = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat YEAR_MONTH_DAY = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DAY = new SimpleDateFormat("dd");
    public static final SimpleDateFormat MONTH = new SimpleDateFormat("MM");

    /**
     * 获取当前时间周几
     *
     * @param timeStamp
     * @return
     */
    public static String getWeek(long timeStamp) {
        int mydate = 0;
        String week = null;
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(timeStamp));
        mydate = cd.get(Calendar.DAY_OF_WEEK);
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = "周日";
        } else if (mydate == 2) {
            week = "周一";
        } else if (mydate == 3) {
            week = "周二";
        } else if (mydate == 4) {
            week = "周三";
        } else if (mydate == 5) {
            week = "周四";
        } else if (mydate == 6) {
            week = "周五";
        } else if (mydate == 7) {
            week = "周六";
        }
        return week;
    }


    /**
     * 数字转月份
     *
     * @param month
     * @return
     */
    public static String getMoth(int month) {
        String toMonth = "";
        // 获取指定日期转换成星期几
        if (month == 1) {
            toMonth = "一";
        } else if (month == 2) {
            toMonth = "二";
        } else if (month == 3) {
            toMonth = "三";
        } else if (month == 4) {
            toMonth = "四";
        } else if (month == 5) {
            toMonth = "五";
        } else if (month == 6) {
            toMonth = "六";
        } else if (month == 7) {
            toMonth = "七";
        } else if (month == 8) {
            toMonth = "八";
        } else if (month == 9) {
            toMonth = "九";
        } else if (month == 10) {
            toMonth = "十";
        } else if (month == 11) {
            toMonth = "十一";
        } else if (month == 12) {
            toMonth = "十二";
        }
        return toMonth;
    }

    /**
     * 获取周几和时间
     * 返回 周二  09:20 格式
     *
     * @param timeStamp
     * @return
     */
    public static String getWeekAndTime(long timeStamp) {
        return getWeek(timeStamp) + " " + milliseconds2String(timeStamp, TIME_MILLION);
    }

    private static String getDayOfWeek(int day) {
        String dayOfWeek = "";
        switch (day) {
            case Calendar.SUNDAY:
                dayOfWeek = "日";
                break;
            case Calendar.MONDAY:
                dayOfWeek = "一";

                break;
            case Calendar.TUESDAY:
                dayOfWeek = "二";

                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "三";

                break;
            case Calendar.THURSDAY:
                dayOfWeek = "四";

                break;
            case Calendar.FRIDAY:
                dayOfWeek = "五";

                break;
            case Calendar.SATURDAY:
                dayOfWeek = "六";
                break;
            default:
                break;
        }
        return dayOfWeek;
    }

    public static String today() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }

    public static String milliseconds2StringMinute(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF1);
    }


    /**
     * 将时间戳转为时间字符串
     * <p>格式为 HH:mm</p>
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String milliseconds2StringHour(long milliseconds) {
        return milliseconds2String(milliseconds, TIME_MILLION);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为 yyyy-MM-dd</p>
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String milliseconds2StringYeaer(long milliseconds) {
        return milliseconds2String(milliseconds, YEAR_MONTH_DAY);
    }


    /**
     * 将时间戳转为时间字符串
     * <p>格式为 HH:mm:ss</p>
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String milliseconds2StringHourSecondM(long milliseconds) {
        return milliseconds2String(milliseconds, TIME_SECCOND_MILLION);
    }


    public static String milliseconds2StringDay(long milliseconds) {
        return milliseconds2String(milliseconds, DAY);
    }


    /**
     * 将时间戳转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param milliseconds 毫秒时间戳
     * @param format       时间格式
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds, SimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }

    /**
     * 毫秒转换为小时分钟
     *
     * @param milliseconds
     * @return
     */
    public static String millisecondsToHour(long milliseconds) {

        Date date = new Date(milliseconds);


        Calendar current = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    /**
     * 获取指定某一天0点0分0秒时间戳
     *
     * @param date eg:17/12/21
     */
    public static long getSpecifyDateStart(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        Date parse = sdf.parse(date);
        return parse.getTime() / 1000; //单位为秒
    }

    /**
     * 获取指定某一天23点59分59秒时间戳
     *
     * @param date eg:17/12/21
     */
    public static long getSpecifyDateEnd(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        Date parse = sdf.parse(date);
        return parse.getTime() / 1000 + 24 * 60 * 60; //单位为秒
    }

    /**
     * 根据时间戳获取日期
     */
    public static String getFormatTimeInMillis(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        Date date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd", Locale.CHINA);

        return dateFormat.format(date);
    }


    /**
     *
     */
    public static String dateToMonth(String strDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM", Locale.CHINA);

        return dateFormat.format(strtodate);
    }


    public static String getSpecialDate(long specialTime) {
        long current = System.currentTimeMillis();//当前时间毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve = zero + 24 * 60 * 60 * 1000;//今天23点59分59秒的毫秒数

        if (specialTime >= zero && specialTime < twelve) {
            return "今天" + milliseconds2StringHour(specialTime);
        } else if (specialTime >= twelve && specialTime < twelve + 24 * 60 * 60 * 1000) {
            return "明天" + milliseconds2StringHour(specialTime);
        } else if (specialTime >= twelve + 24 * 60 * 60 * 1000 && specialTime < twelve + 2 * (24 * 60 * 60 * 1000)) {
            return "后天" + milliseconds2StringHour(specialTime);
        }

        return formatTimeInMillisWithMMDD(specialTime);
    }

    /**
     * 返回的字符串形式是形如 20:58
     */
    public static String formatTimeInMillisWithMMDD(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        Date date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM-dd HH:mm");
        return dateFormat.format(date);
    }

    private static String mYear; // 当前年
    private static String mMonth; // 月
    private static String mDay;
    private static String mWay;

    /**
     * 获取今天往后一周的日期（几月几号）
     */
    public static List<DateBean> getSevendate() {

        List<DateBean> dates = new ArrayList<DateBean>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        for (int i = 0; i < 7; i++) {
            DateBean dateBean = new DateBean();

            String oldDate = getOldDate(i);

            mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
            mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
            mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + i);// 获取当前日份的日期号码
            String date = mMonth + "月" + mDay + "日";
            dateBean.setDate(oldDate);
            if (i==0){
                dateBean.setSelect(true);
            }else {
                dateBean.setSelect(false);
            }
            dates.add(dateBean);
        }
        return dates;
    }

    /**
     * 获取今天往后一周的日期（几月几号）
     */
    public static List<DateBean> getSevendate(long time) {

        List<DateBean> dates = new ArrayList<DateBean>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//        c.setTimeInMillis(time);
        for (int i = 0; i < 7; i++) {
            DateBean dateBean = new DateBean();
            mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
            mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
            mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + i);// 获取当前日份的日期号码
            String date = mMonth + "月" + mDay + "日";
            dateBean.setDate(date);
            if (i==0){
                dateBean.setSelect(true);
            }else {
                dateBean.setSelect(false);
            }
            dates.add(dateBean);
        }
        return dates;
    }

//    /**
//     * 获取今天往后一周的日期（几月几号）
//     */
//    public static List<DateBean> getSevendate() {
//        List<DateBean> dates = new ArrayList<DateBean>();
//        final Calendar c = Calendar.getInstance();
//        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//
//
//        for (int i = 0; i < 7; i++) {
//            DateBean dateBean = new DateBean();
//            mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
//            mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
//            mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + i);// 获取当前日份的日期号码
//            if(Integer.parseInt(mDay) > MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear),(i+1))){
//                mDay = String.valueOf(MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear),(i+1)));
//            }
//
//            if (i==0){
//                dateBean.setSelect(true);
//            }else {
//                dateBean.setSelect(false);
//            }
//            String date = mMonth + "月" + mDay + "日";
//            dateBean.setDate(date);
//            dates.add(dateBean);
//        }
//        return dates;
//    }

    /**得到当年当月的最大日期**/
    public static int MaxDayFromDay_OF_MONTH(int year,int month){
        Calendar time= Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR,year);
        time.set(Calendar.MONTH,month-1);//注意,Calendar对象默认一月为0
        int day=time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }

    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */
    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("MM月dd日");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }
}
