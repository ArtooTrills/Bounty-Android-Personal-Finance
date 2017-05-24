package com.examples.ankit.breakpoint.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

}
