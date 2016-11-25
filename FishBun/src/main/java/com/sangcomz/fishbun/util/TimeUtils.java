package com.sangcomz.fishbun.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dong on 2015-09-20.
 */
public class TimeUtils {

    //현재시간 unixTimeStamp
    public static long getCurrentUnixTimeStamp() {
        return (System.currentTimeMillis() / 1000);
    }

    //오늘 unixTimeStamp
    public static long getTodayUnixTimeStamp() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return (calendar.getTimeInMillis() / 1000);
    }

    //내일 unixTimeStamp
    public static long getTomorrowUnixTimeStamp(long unixTimestampToday) {
        return unixTimestampToday + (24 * 60 * 60);
    }

    //어제 unixTimeStamp
    public static long getYesterdayUnixTimeStamp(long unixTimestampToday) {
        return unixTimestampToday - (24 * 60 * 60);
    }

    //시의 timeStamp 구하기
    public static long getHourUnixTimeStamp(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis() / 1000;
    }

    //시, 분의 timeStamp 구하기
    public static long getHourMinuteUnixTimeStamp(int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        //Log용
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);
//        String strTime = sdf.format(calendar.getTimeInMillis());  //unix timeStamp -> String
//        Log.e(TAG, "unix time " + calendar.getTimeInMillis());
//        Log.e(TAG,"unix time String " + strTime);

        return (calendar.getTimeInMillis() / 1000);
    }

    //unixTimeStamp -> Date -> String(yyyy-MM-dd)
    public static String unixTimeStampToStringDate(long timeStamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);
        SimpleDateFormat convertedSdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

        // -9시간 되는 버그...극혐
//        sdf.setTimeZone(TimeZone.getTimeZone("KST"));
//        convertedSdf.setTimeZone(TimeZone.getTimeZone("KST"));

        String strTime = sdf.format(timeStamp * 1000);  //unix timeStamp -> String

        Date timeDate = null;  //String -> Date
        try {
            timeDate = sdf.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = convertedSdf.format(timeDate); //Date -> String

        return convertedDate;
    }

    //Todo: Am, Pm 처리
    //unixTimeStamp -> Date -> String(HH:mm)
    public static String unixTimeStampToStringTime(long timeStamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);
        SimpleDateFormat convertedSdf = new SimpleDateFormat("HH : mm", Locale.KOREA);

        String strTime = sdf.format(timeStamp * 1000);  //unix timeStamp -> String

        Date timeDate = null;  //String -> Date
        try {
            timeDate = sdf.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = convertedSdf.format(timeDate); //Date -> String

        return convertedDate;
    }

    //String(yyyy-MM-dd) -> unixTimeStamp
    public static long stringDayToUnixTimeStamp(String strDay) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date parseDate = null;

        try {
            parseDate = sdf.parse(strDay);
            Log.d("TimeUtils", " " + parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("TimeUtils", " " + parseDate.getTime());

        return (parseDate.getTime() / 1000);
    }

    //unixTimeStamp -> String(yyyy년 MM월 dd일)
    public static String unixTimeStampToStringDateYearMonthDay(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp * 1000);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);

        String result;

        if (month < 10) {
            result = year + "년 0" + month + "월 " + day + "일";
        } else {
            result = year + "년 " + month + "월 " + day + "일";
        }

        return result;
    }

    //unixTimeStamp -> String(yyyy. MM. dd. (D) _ HH:MM)
    public static String unixTimeStampToStringDateYearMonthDayHourMinute(long timeStamp) {

        final String strDayOfWeek[] = {"일", "월", "화", "수", "목", "금", "토"};

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeStamp * 1000);

        int year = c.get(Calendar.YEAR);
        int monthOfYear = c.get(Calendar.MONTH) + 1;
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);  //요일

        String result;

        if (monthOfYear < 10) {
            result = year + ". 0" + monthOfYear + ". " + dayOfMonth + " (" + strDayOfWeek[dayOfWeek - 1]
            + ") _ " + hourOfDay + ":" + minute;
        } else {
            result = year + ". " + monthOfYear + ". " + dayOfMonth + " (" + strDayOfWeek[dayOfWeek - 1]
                    + ") _ " + hourOfDay + ":" + minute;
        }

        return result;
    }

    //unixTimeStamp -> String(MM월 dd일)
    public static String unixTimeStampToStringDateMonthDay(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp * 1000);

        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);

        String result = month + "월 " + day + "일";

        return result;
    }

    // 해당일로부터 after만큼 후의 timestamp를 리턴
    // ex. 하루 뒤, 2일 뒤
    public static long getAfterDayUnixTimeStamp(long unixTimestampToday, int after) {
        return unixTimestampToday + (after * 24 * 60 * 60);
    }

    //원하는 날의 timestamp 리턴
    public static long getDayTimeStamp(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return (calendar.getTimeInMillis() / 1000);
    }

    //원하는 날의 timestamp 리턴
    public static long getDayTimeStamp(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return (calendar.getTimeInMillis() / 1000);
    }

    //timestamp에서 년 추출
    public static int timestampToYear(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp * 1000);

        int year = calendar.get(Calendar.YEAR);

        return year;
    }

    //timestamp에서 월 추출
    public static int timestampToMonth(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp * 1000);

        int month = calendar.get(Calendar.MONTH) + 1;

        return month;
    }

    //timestamp에서 일 추출
    public static int timestampToDay(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp * 1000);

        int day = calendar.get(Calendar.DATE);

        return day;
    }

    //timestamp에서 시 추출
    public static int timestampToHour(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000);

        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        return hour;
    }

    //timestamp에서 분 추출
    public static int timestampToMinute(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000);

        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        int minute = calendar.get(Calendar.MINUTE);

        return minute;
    }
}
