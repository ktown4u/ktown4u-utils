package com.ktown4u.utils;

import java.util.regex.Pattern;

public class Neutralizer {
    public static final String LOCAL_DATE_TIME_REPLACEMENT = "2003-05-03T10:11:12.134567";
    private static final Pattern localDateTimeePattern =
            Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}");

    public static String localDateTime(String string) {
        return localDateTimeePattern
                .matcher(string)
                .replaceAll(LOCAL_DATE_TIME_REPLACEMENT);
    }
}