package com.wlwq.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * 时间工具类
 *
 * @author wlwq
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    public static Date add(@NonNull Date date, long time, @NonNull TimeUnit timeUnit) {
        Assert.notNull(date, "Date must not be null");
        Assert.isTrue(time >= 0, "Addition time must not be less than 1");
        Assert.notNull(timeUnit, "Time unit must not be null");

        Date result;

        int timeIntValue;

        if (time > Integer.MAX_VALUE) {
            timeIntValue = Integer.MAX_VALUE;
        } else {
            timeIntValue = Long.valueOf(time).intValue();
        }

        // Calc the expiry time
        switch (timeUnit) {
            case DAYS:
                result = org.apache.commons.lang3.time.DateUtils.addDays(date, timeIntValue);
                break;
            case HOURS:
                result = org.apache.commons.lang3.time.DateUtils.addHours(date, timeIntValue);
                break;
            case MINUTES:
                result = org.apache.commons.lang3.time.DateUtils.addMinutes(date, timeIntValue);
                break;
            case SECONDS:
                result = org.apache.commons.lang3.time.DateUtils.addSeconds(date, timeIntValue);
                break;
            case MILLISECONDS:
                result = org.apache.commons.lang3.time.DateUtils.addMilliseconds(date, timeIntValue);
                break;
            default:
                result = date;
        }
        return result;
    }

    /**
     * 根据返回的weekKey判断是周几
     *
     * @param weekKey
     * @return
     */
    public static String getWeekName(Integer weekKey) {
        if (weekKey == 7) {
            return "周日";
        }
        if (weekKey == 1) {
            return "周一";
        }
        if (weekKey == 2) {
            return "周二";
        }
        if (weekKey == 3) {
            return "周三";
        }
        if (weekKey == 4) {
            return "周四";
        }
        if (weekKey == 5) {
            return "周五";
        }
        if (weekKey == 6) {
            return "周六";
        }
        return null;
    }


    /**
     * 根据指定时间查看周几
     *
     * @return
     */
    public static WeekVO getWeek(Date date) {
        WeekVO weekVO;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekIdx = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (weekIdx) {
            case 1:
                weekVO = WeekVO.builder().num(weekIdx).name("周一").build();
                break;
            case 2:
                weekVO = WeekVO.builder().num(weekIdx).name("周二").build();
                break;
            case 3:
                weekVO = WeekVO.builder().num(weekIdx).name("周三").build();
                break;
            case 4:
                weekVO = WeekVO.builder().num(weekIdx).name("周四").build();
                break;
            case 5:
                weekVO = WeekVO.builder().num(weekIdx).name("周五").build();
                break;
            case 6:
                weekVO = WeekVO.builder().num(weekIdx).name("周六").build();
                break;
            default:
                weekVO = WeekVO.builder().num(weekIdx).name("周日").build();
                break;
        }
        return weekVO;
    }

    /**
     * 周列表
     *
     * @return
     */
    public static List<WeekVO> getWeekList() {
        String[] arr = {"一", "二", "三", "四", "五", "六", "日"};
        List<WeekVO> weekVOList = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            weekVOList.add(WeekVO.builder().num(i).name("周"+arr[i-1]).build());
        }
        return weekVOList;
    }


    /**
     * Create By Renbowen
     *
     * @Date: 2020/8/18 21:20
     * @Description:转换时间
     */
    public static String convertToTimeString(Date time) {
        Integer weekKey = DateUtil.dayOfWeek(time);
        // 当前时间
        Date nowDate = DateUtil.date();

        StringBuilder result = new StringBuilder();
        // 如果当前时间大于需要转换的时间
        if (nowDate.getTime() > time.getTime()) {
            // 今天最大的时间
            Date todayMaxDate = DateUtil.endOfDay(nowDate);
            // 计算两个时间相差多少秒
            long betweenSecond = DateUtil.between(time, nowDate, DateUnit.SECOND);
            // 当前时间距今天剩余秒数
            long maxBetweenSecond = DateUtil.between(time, todayMaxDate, DateUnit.SECOND);
            // 今天剩余秒数
            long todayBetweenSecond = DateUtil.between(nowDate, todayMaxDate, DateUnit.SECOND);
            // 如果秒数小于一年
            if (betweenSecond < 31536000) {
                // 如果两个时间差 小于 今天剩余秒数
                if (todayBetweenSecond > maxBetweenSecond) {
                    // 如果是一分钟之内
                    if (betweenSecond < 60) {
                        result.append("刚刚").append("(").append(getWeekName(weekKey)).append(")");
                    } else if (betweenSecond < 3600) {
                        result.append(betweenSecond / 60).append("分钟前").append("(").append(getWeekName(weekKey)).append(")");
                    } else {
                        result.append(betweenSecond / 3600).append("小时前").append("(").append(getWeekName(weekKey)).append(")");
                    }
                } else {
                    // 获取两个时间相差多少天  此方法精确到秒 未满86400秒则不算一天
                    long betweenDay = compareDays(time, nowDate);
                    if (betweenDay == 1) {
                        result.append("昨天").append("(").append(getWeekName(weekKey)).append(")").append(DateUtil.format(time, "HH:mm:ss"));
                    } else if (betweenDay == 2) {
                        result.append("前天").append("(").append(getWeekName(weekKey)).append(")").append(DateUtil.format(time, "HH:mm:ss"));
                    } else if (betweenDay == 3) {
                        result.append(betweenDay).append("(").append(getWeekName(weekKey)).append(")").append("天前").append(DateUtil.format(time, "HH:mm:ss"));
                    } else {
                        result.append(DateUtil.format(time, "yyyy-MM-dd HH:mm:ss")).append("(").append(getWeekName(weekKey)).append(")");
                    }
                }
            } else {
                result.append(DateUtil.format(time, "yyyy-MM-dd HH:mm:ss")).append("(").append(getWeekName(weekKey)).append(")");
            }
        }
        // 如果当前时间小于等于需要转换的时间
        else {
            // 获取两个时间相差多少天  此方法精确到秒 未满86400秒则不算一天
            long betweenDay = compareDays(nowDate, time);
            if (betweenDay == 0) {
                result.append("今天").append("(").append(getWeekName(weekKey)).append(")").append(DateUtil.format(time, "HH:mm:ss"));
            } else if (betweenDay == 1) {
                result.append("明天").append("(").append(getWeekName(weekKey)).append(")").append(DateUtil.format(time, "HH:mm:ss"));
            } else if (betweenDay == 2) {
                result.append("后天").append("(").append(getWeekName(weekKey)).append(")").append(DateUtil.format(time, "HH:mm:ss"));
            } else {
                result.append(DateUtil.format(time, "yyyy-MM-dd HH:mm:ss")).append("(").append(getWeekName(weekKey)).append(")");
            }
        }
        return result.toString();
    }

    /**
     * Create By Renbowen
     *
     * @Date: 2020/8/18 21:10
     * @Description: 计算两个日期相差多少天
     */
    public static Integer compareDays(Date date1, Date date2) {
        int day = 0;
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int year1 = calendar1.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);
        if (year1 > year2) {
            int tempyear = year1;
            int tempday = day1;
            day1 = day2;
            day2 = tempday;
            year1 = year2;
            year2 = tempyear;
        }
        if (year1 == year2) {
            day = (day2 - day1);
        } else {
            int DayCount = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    DayCount += 366;
                } else {
                    DayCount += 365;
                }
            }
            day = DayCount + (day2 - day1);
        }
        return day;
    }

/*    public static void main(String[] args) {
        Integer num = compareDays(new Date("2023/9/17"), new Date("2023/9/15"));
        System.out.println(num);
    }*/
    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }


    /**
     * 两个日期相减  页面显示几分前，几小时前
     *
     * @param now
     * @param early
     * @return
     */
    public static String dateDifferenceDesc(Date now, Date early) {
        String res = "";
        if (null != now && null != early) {
            Long preTime = now.getTime() - early.getTime();
            if (preTime < 60000L) {
                res = "1分钟前";
            } else if (preTime < 3600000L) {
                res = (preTime / 60000L) + "分钟前";
            } else if (preTime < 86400000L) {
                res = (preTime / 3600000L) + "小时前";
            } else if (preTime < 172800000L) {
                res = "昨天";
            } else if (preTime < 259200000L) {
                res = "前天";
            } else if (preTime < 31536000000L) {
                res = (preTime / 86400000L) + "天前";
            } else {
                res = (preTime / 31536000000L) + "年前";
            }
        }
        return res;
    }

    /**
     * 判断某个时间是否是在当前时间的七天之内
     *
     * @param addtime
     * @param now
     * @return
     */
    public static boolean isLatestWeek(Date addtime, Date now) {
        //得到日历
        Calendar calendar = Calendar.getInstance();
        //把当前时间赋给日历
        calendar.setTime(now);
        //设置为7天前
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        //得到7天前的时间
        Date before7days = calendar.getTime();
        if (before7days.getTime() < addtime.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将当前的时间加上分钟
     *
     * @param min
     * @return
     */
    public static Date addMin(Integer min) {
        // 将当前的时间加上分钟
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, min);
        System.out.println(nowTime.getTime());
        return nowTime.getTime();
    }

    /**
     * 将时间转换为毫秒值
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static long time(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = sdf.parse(date).getTime();
        return time;
    }

    /**
     * 根据月份查询有多少天
     * @param mon
     * @return
     */
    public static Integer day(String mon){
        Integer day = 0;
        String[] arr = mon.split("-");
        int year= Convert.toInt(arr[0]),month=Convert.toInt(arr[1]);
        if(month==2) {
            //判断年是不是闰年
            if((year%4==0&&year%100!=0)||(year%400==0)) {
                day = 29;
            }else {
                day = 28;
            }
        }else if(month==4||month==6||month==9||month==11) {
            day = 30;
        }else if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
            day = 31;
        }
        return day;
    }

    /**
     * java查询本月已过天数
     * @param
     */
    public static int alreadyDay() {
        String format = DateUtil.format(DateUtil.date(), "dd");
        if(Convert.toInt(format) < 10){
            format = format.substring(1,2);
        }
        return Convert.toInt(format);
    }


    /**
     * java比较两个月是否相等 0:相等 1：大于 2：小于
     * @param
     */
    public static int monthCompare(String month) {
        if(StringUtils.isBlank(month)){
           return 1;
        }
        month = month.replaceAll("-","");
        // 当前月份
        String format = DateUtil.format(DateUtil.date(), "yyyyMM");
        if(Convert.toInt(month) > Convert.toInt(format)){
            return 1;
        }
        if(Convert.toInt(month) < Convert.toInt(format)){
            return 2;
        }
        return 0;
    }

}
