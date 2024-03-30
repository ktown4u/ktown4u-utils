package com.ktown4u.utils;

public class LineFormatter {

    private int columns;

    public LineFormatter(int columns) {
        this.columns = columns;
    }

    public String formatLineWithWhitespaces(final String name, final Object value) {
        final int whitespaceSize = this.columns - keyLength(name) - valueLength(value);
        return String.format("%s%s%s\n", name,
                " ".repeat(Math.max(0, whitespaceSize)),
                value);
    }

    private int keyLength(String name) {
        return name.length();
    }

    private int valueLength(Object value) {
        return null == value ? "null".length() : value.toString().length();
    }

    public String formatLineWithWhitespaces(int depth, String name, String value) {
        String indentedName = " ".repeat(depth * 4) + name;
        return formatLineWithWhitespaces(indentedName, value);
    }
}