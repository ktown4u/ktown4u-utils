package com.ktown4u.utils;

class DiffStringBuilder {

    private final StringBuilder diffBuilder;

    private DiffStringBuilder(StringBuilder diffBuilder) {
        this.diffBuilder = diffBuilder;
    }

    public static DiffStringBuilder init() {
        return new DiffStringBuilder(new StringBuilder());
    }

    public void appendConcur(final String fieldName, final Object value) {
        diffBuilder.append(fieldName)
                .append(": ")
                .append(value)
                .append("\n");
    }

    public void appendDiff(final String fieldName, final Object beforeValue, final Object afterValue) {
        diffBuilder.append(fieldName)
                .append(": ")
                .append(beforeValue)
                .append(" -> ")
                .append(afterValue)
                .append("\n");
    }

    public String toString() {
        return diffBuilder.toString();
    }
}
