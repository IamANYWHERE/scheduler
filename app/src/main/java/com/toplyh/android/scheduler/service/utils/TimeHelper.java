package com.toplyh.android.scheduler.service.utils;

import android.text.format.DateUtils;

import com.toplyh.android.scheduler.ui.adapter.MeetingAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 我 on 2017/12/27.
 */

public class TimeHelper {

    //1000*24*60*60
    private static long nd=86400000;
    //1000*60*60
    private static long nh=3600000;
    //1000*60
    private static long nm=60000;



    public static int getDateDiff(Date nowDate, Date dllDate){

        //获取两个时间的毫秒时间差异
        long diff=dllDate.getTime()-nowDate.getTime();
        diff=Math.abs(diff);
        //计算差多少天
        long day=diff/nd;
        if (day<=7){
            return 0;
        }else if (day>7&&day<=21){
            return 1;
        } else {
            return 2;
        }
    }
    
    public static String getDateDiffDesc(Date nowDate, Date dllDate) {
        //获取两个时间的毫秒时间差异
        long diff = dllDate.getTime() - nowDate.getTime();
        //计算差多少天
        long day = diff / nd;
        return day + "";
    }

    public static MeetingAdapter.MeetingStatus getDatePassed(Date currentDate, Date meetingDate){

        long diff=meetingDate.getTime()-currentDate.getTime();

        long day=diff/nd;

        if (day>0){
            return MeetingAdapter.MeetingStatus.INACTIVE;
        }else if (day==0){
            return MeetingAdapter.MeetingStatus.ACTIVE;
        } else {
            return MeetingAdapter.MeetingStatus.COMPLETED;
        }
    }

    public static String parseDateTime(String dateString, String originalFormat, String outputFromat){

        SimpleDateFormat formatter = new SimpleDateFormat(originalFormat, Locale.US);
        Date date = null;
        try {
            date = formatter.parse(dateString);

            SimpleDateFormat dateFormat=new SimpleDateFormat(outputFromat, new Locale("US"));

            return dateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String parseDateSimple(Date date,String outputFromat){
        SimpleDateFormat dateFormat=new SimpleDateFormat(outputFromat,new Locale("US"));
        return dateFormat.format(date);
    }

    public static String getRelativeTimeSpan(String dateString, String originalFormat){

        SimpleDateFormat formatter = new SimpleDateFormat(originalFormat, Locale.US);
        Date date = null;
        try {
            date = formatter.parse(dateString);

            return DateUtils.getRelativeTimeSpanString(date.getTime()).toString();

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
