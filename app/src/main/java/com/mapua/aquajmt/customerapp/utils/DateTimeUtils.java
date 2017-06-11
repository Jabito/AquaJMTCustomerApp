package com.mapua.aquajmt.customerapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bryan on 6/11/2017.
 */

public class DateTimeUtils {
    private static final SimpleDateFormat dateFormatter =
            new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat timeFormatter =
            new SimpleDateFormat("HH:mm:ss");

    private static final SimpleDateFormat dateTimeFormatter =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static Date parseISO8601DateTime(String dateTimeStr) throws ParseException {
        return dateTimeFormatter.parse(dateTimeStr);
    }

    public static String stringifyISO8601DateTime(Date dateTime) {
        return dateTimeFormatter.format(dateTime);
    }

    public static Date parseDate(String dateStr) throws ParseException {
        return dateFormatter.parse(dateStr);
    }

    public static String stringifyDate(Date date) {
        return dateFormatter.format(date);
    }

    public static Date parseTime(String timeStr) throws ParseException {
        return timeFormatter.parse(timeStr);
    }

    public static String stringifyTime(Date date) {
        return timeFormatter.format(date);
    }
}
