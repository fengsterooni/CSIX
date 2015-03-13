package org.csix.android.csix.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String getDateString(Date date){
        SimpleDateFormat MMMddyyyyFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        String dateToString = MMMddyyyyFormat.format(date);
        return dateToString;
    }

    public static String getDayString(Date date){
        SimpleDateFormat ddFormat = new SimpleDateFormat("dd");
        String day= ddFormat.format(date);
        return day;
    }

    public static String getMonthString(Date date){
        SimpleDateFormat MMMMFormat = new SimpleDateFormat("MMMM");
        String month = MMMMFormat.format(date);
        return month;
    }

    public static String getShortMonthString(Date date){
        SimpleDateFormat MMMFormat = new SimpleDateFormat("MMM");
        String month = MMMFormat.format(date);
        return month;
    }

    public static String getDayOfWeekString(Date date){
        SimpleDateFormat EEEEFormat = new SimpleDateFormat("EEEE");
        String dayOfWeek = EEEEFormat.format(date);
        return dayOfWeek;
    }

    public static String getShortDayOfWeekString(Date date){
        SimpleDateFormat EEEFormat = new SimpleDateFormat("EEE");
        String dayOfWeek = EEEFormat.format(date);
        return dayOfWeek;
    }

    public static String getYearString(Date date){
        SimpleDateFormat yyFormat = new SimpleDateFormat("yyyy");
        String dateToString = yyFormat.format(date);
        return dateToString;
    }

    public static String getTimeString(Date date){
        SimpleDateFormat HHmmFormat = new SimpleDateFormat("HH:mm", Locale.US);
        return HHmmFormat.format(date);
    }

    public static String getTime12(String time){
        int newTime = 0;
        String exactTime;
        //time = time.substring(0,2);
        //System.out.println("#######" + time);
        time = time.substring(0, time.indexOf(':')!=-1?time.indexOf(':'):time.length());//IN windows Date comes as 10:00
        if(Integer.parseInt(time) > 12){
            newTime = Integer.parseInt(time.substring(0, 2)) -12;
            exactTime = Integer.toString(newTime) + ":00 PM";
        }else if(Integer.parseInt(time) == 12){
            exactTime = time + ":00 PM";
        }else{
            exactTime = time + ":00 AM";
        }
        return exactTime;
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static Date getDateFromString(String string) {
        Date date = null;
        SimpleDateFormat MMMddyyyyFormat = new SimpleDateFormat("MMM dd, yyyy");
        try {
            date = MMMddyyyyFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long getDateLong(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getTimeInMillis();
    }
}
