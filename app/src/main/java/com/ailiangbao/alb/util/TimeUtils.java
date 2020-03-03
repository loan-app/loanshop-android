package com.ailiangbao.alb.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hll on 2019/3/9
 */
public class TimeUtils {
    @SuppressLint("SimpleDateFormat")
    public static String dateToStamp(String s) {
        if (TextUtils.isEmpty(s)) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(s);
            long time = date.getTime();
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date2 = new Date(time);
            return simpleDateFormat1.format(date2);
        } catch (Exception e) {
            return s;
        }
    }

    /**
     * @param dates 要加时间的起点
     * @param days  要加的天数
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String handlerPayBackMoneyTime(String dates, int days) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dates);
            long time = date.getTime();
            long b = time + (days * 86400000);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date2 = new Date();
            date2.setTime(b);
            return simpleDateFormat2.format(date2);
        } catch (Exception e) {
            return dates;
        }
    }
}
