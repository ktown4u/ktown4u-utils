package com.ktown4u.utils;

public class LineFormatter {
    public LineFormatter() {
    }

    String formatLineWithWhitespaces(final String name, final Object value, int columns) {
        final int length = null == value ? 4 : value.toString().length();
        final int whitespaceSize = columns - name.length() - length;
        return String.format("%s%s%s\n", name, " ".repeat(Math.max(0, whitespaceSize)), value);
    }
}