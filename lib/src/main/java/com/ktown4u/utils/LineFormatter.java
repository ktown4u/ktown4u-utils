package com.ktown4u.utils;

public class LineFormatter {

    private final int columns;

    public LineFormatter(final int columns) {
        this.columns = columns;
    }

    public String formatLineWithWhitespaces(final String name, final Object value) {
        final int whitespaceSize = columns - keyLength(name) - valueLength(value);
        return String.format("%s%s%s\n", name,
                repeat(" ", Math.max(0, whitespaceSize)),
                value);
    }

    private int keyLength(final String name) {
        return name.length();
    }

    private int valueLength(final Object value) {
        return null == value ? "null".length() : value.toString().length();
    }

    public String formatLineWithWhitespaces(final int depth, final String name, final String value) {
        final String indentedName = repeat(" ", depth * 4) + name;
        return formatLineWithWhitespaces(indentedName, value);
    }

    private String repeat(final String str, final int count) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}