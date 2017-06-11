package com.examples.ankit.breakpoint.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ankit on 22/05/17.
 */

public class DateUtil {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy");

    /**
     * Converts date into DD Month Year format
     */
    public static String dateToString(Date date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * Returns month of given timestamp.
     */
    public static int getMonthFromDate(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * Returns short string for a given index of a month. If index is out of bound it returns empty
     * string.
     */
    public static String getMonthName(int month) {
        String monthName = "";
        if (month >= 0 && month < 12) {
            monthName = new DateFormatSymbols().getShortMonths()[month];
        }
        return monthName;
    }
}
