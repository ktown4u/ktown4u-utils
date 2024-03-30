package com.ktown4u.utils;

import java.util.regex.Pattern;

public class Neutralizer {
    public Neutralizer() {
    }

    String localDateTime(String string) {
        String replacement = "2003-05-03T10:11:12.134567";
        String regex = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(string).replaceAll(replacement);
    }
}