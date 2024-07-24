package com.ktown4u.utils;

public class DiffStringBuilder {

    private final StringBuilder diffBuilder = new StringBuilder();

    public static DiffStringBuilder init() {
        return new DiffStringBuilder();
    }

    public void appendConcur(final String fieldName, final Object value) {
        diffBuilder.append(fieldName)
                .append(": ")
                .append(value)
                .append("\n");
    }

    public void appendDiff(final String fieldName, final Object value1, final Object value2) {
        diffBuilder.append(fieldName)
                .append(": ")
                .append(value1)
                .append(" -> ")
                .append(value2)
                .append("\n");
    }

    public String toString() {
        return diffBuilder.toString();
    }
}
