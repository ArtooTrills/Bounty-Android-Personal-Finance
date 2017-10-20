package com.example.nazmuddinmavliwala.ewallet.domain.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtil {
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public static final String DATE_DAY_FORMAT = "yyyy-MM-dd";
    private static final String TODAY_TAG = "Today";
    private static final String COMMA_SEPERATOR = ",";
    private static final String PRETTY_DATE_FORMAT = "EEE, dd MMM";
    private static final String PRETTY_TIME_FORMAT = "h:mma";
    private static final String TIME_FROM_FORMAT = "kk:mm:ss";
    private static final String SPACE_SEPERATOR = " ";
    public static final String PRETTY_MONTH_FORMAT = "MMM yyyy";
    private static final String PRETTY_INTERVIEW_MONTH_FORMAT = "dd MMM yyyy";


    public static String CURRENT_DATE = "2015-02-06";


    public static String getTodayDate() {
        sdf.applyPattern(FORMAT_DATE_TIME);
        return format(new Date());
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH)
                .format(Calendar.getInstance().getTime());
    }

    public static String getCurrentTime(String format) {
        return new SimpleDateFormat(format, Locale.ENGLISH)
                .format(Calendar.getInstance().getTime());
    }

    public static long getTimeFor(String date) {
        return formatDate(date, "yyyy/MM/dd HH:mm:ss").getTime();
    }

    public static long getTimeDifference(String time) {
        String currentTime = getCurrentTime();


        Date current_date = new Date();
        current_date = formatDate(currentTime, "yyyy/MM/dd HH:mm:ss");

        Date previous_date = formatDate(time, "yyyy/MM/dd HH:mm:ss");


        long diff = current_date.getTime() - previous_date.getTime();
        return diff / (1000 * 60 * 60);
    }

    public static long getDaysDiff(String time) {
        return getTimeDifference(time) / 24;
    }

    public static boolean isBeforeToday(String date) {
        Date current_date = new Date();
        current_date = formatDate(current_date, DATE_DAY_FORMAT);
        return formatDate(date, DATE_DAY_FORMAT).before(current_date);

    }

    public static String getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return sdf.format(new Date());
    }

    public static Date formatDate(String d, String formatter) {
        sdf.applyPattern(formatter);
        return parse(d);
    }

    public static Date formatDate(Date d, String formatter) {
        sdf.applyPattern(formatter);
        return parse(format(d));
    }

    private static Date parse(String d) {
        Date date = null;
        try {
            date = sdf.parse(d);
        } catch (ParseException e) {
            //
        }
        return date;
    }

    private static String format(Date d) {
        try {
            return sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String beautifyDate(String t) {
        String date = null;
        if (isDateToday(t))
            date = TODAY_TAG + COMMA_SEPERATOR;
        else
            date = parseDate(t, DATE_DAY_FORMAT, PRETTY_DATE_FORMAT) + SPACE_SEPERATOR;
        return date;
    }

    public static String beautifyMonth(String t) {
        return parseDate(t, DATE_DAY_FORMAT, PRETTY_MONTH_FORMAT) + SPACE_SEPERATOR;
    }

    public static String beautifyDDMMMYYYY(String t) {
        String date;
        if (isDateToday(t))
            date = TODAY_TAG + COMMA_SEPERATOR;
        else
            date = parseDate(t, DATE_DAY_FORMAT, PRETTY_INTERVIEW_MONTH_FORMAT) + SPACE_SEPERATOR;
        return date;
    }

    public static String parseDate(String t, String dateDayFormat, String prettyDateFormat) {
        sdf.applyPattern(dateDayFormat);
        Date parse = parse(t);
        sdf.applyPattern(prettyDateFormat);
        return format(parse);
    }


    public static boolean isDateToday(String t) {
        Date d = new Date();
        d = formatDate(d, DATE_DAY_FORMAT);
        return formatDate(t, DATE_DAY_FORMAT).equals(d);

    }

    public static String beautifyTime(String t) {
        return parseDate(t, TIME_FROM_FORMAT, PRETTY_TIME_FORMAT);
    }

    public static String prettyConstructSlotDate(String start, String end) {
        String prettyDate = "";
        try {
            String startTime = beautifyTime(start.split("T")[1]);
            String endTime = beautifyTime(end.split("T")[1]);

            String startTimeStamp = startTime.substring(startTime.length() - 2, startTime.length()).equalsIgnoreCase("am") ? "AM" : "PM";
            String endTimeStamp = endTime.substring(endTime.length() - 2, endTime.length()).equalsIgnoreCase("am") ? "AM" : "PM";
            startTime = startTime.substring(0, startTime.length() - 2);
            endTime = endTime.substring(0, endTime.length() - 2) ;
            if(startTimeStamp.equals(endTimeStamp)) {
                prettyDate = prettyDate
                        + " " +
                        startTime + " "
                        + "-" + " "
                        +
                        endTime + " " + endTimeStamp;
            } else {

                prettyDate = prettyDate
                        + " " +
                        startTime + " " + startTimeStamp + " "
                        + "-" + " "
                        +
                        endTime + " " + endTimeStamp;
            }

        } catch (NullPointerException e) {

        }
        return prettyDate;
    }


    public static long getHourDifference(Date time) {
        Date currentDate = Calendar.getInstance().getTime();
        long hours = (currentDate.getTime() - time.getTime()) / (100 * 60 * 60);
        return 0;
    }

    public static boolean isAgeMoreThanFourteenYears(String dateOfBirth) {
        Calendar currentMinusFourteenYears = Calendar.getInstance();
        currentMinusFourteenYears.add(Calendar.YEAR, -14);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDate(dateOfBirth, DATE_DAY_FORMAT));


        return calendar.before(currentMinusFourteenYears);


    }

    public static Date formatDate(String date) {
        return formatDate(date,DATE_DAY_FORMAT);
    }

    public static boolean isDateAfter(String startTime, String endTime) {
        sdf.applyPattern(DATE_DAY_FORMAT);
        Date startDate = parse(startTime);
        Date endDate = parse(endTime);
        return endDate.compareTo(startDate) > 0;
    }

    public static boolean isNotAfterToday(String date) {
        return isDateAfter(date,getToday());
    }

    public static String dateToString(Date date, String format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            // log.error(e);
        }
        return result;
    }
}
