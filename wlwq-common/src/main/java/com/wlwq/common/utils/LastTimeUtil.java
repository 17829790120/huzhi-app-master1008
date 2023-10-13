package com.wlwq.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LastTimeUtil {
    /**
     * 获取本月1号0点0分0秒的时间
     */
    public static String getBeforeFirstMonthdate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至00
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        //将分钟至00
        calendar.set(Calendar.MINUTE, 00);
        //将秒至00
        calendar.set(Calendar.SECOND, 00);
        String format1 = format.format(calendar.getTime());
        return format1;
    }

    /**
     * 获取本月的最后一天23点59分59秒的时间
     */
    public static String getBeforeLastMonthdate() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        calendar.set(Calendar.MINUTE, 59);
        //将秒至59
        calendar.set(Calendar.SECOND, 59);
        String format = sf.format(calendar.getTime());
        return format;
    }

    public static void main(String[] args) {
        System.out.println(getBeforeFirstMonthdate()+"----------------"+getBeforeLastMonthdate());
    }
    /**
     * 获取上个月月份
     * @return
     */
    public static final String getLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        // 设置为当前时间
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-1);
        date = calendar.getTime();
        return format.format(date);
    }
}
