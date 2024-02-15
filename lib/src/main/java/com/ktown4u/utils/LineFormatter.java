package com.ktown4u.utils;

public class LineFormatter {

    private int columns;

    public LineFormatter(int columns) {
        this.columns = columns;
    }

    public String formatLineWithWhitespaces(final String name, final Object value) {
        final int length = null == value ? 4 : value.toString().length();
        final int whitespaceSize = this.columns - name.length() - length;
        return String.format("%s%s%s\n", name, " ".repeat(Math.max(0, whitespaceSize)), value);
    }
}