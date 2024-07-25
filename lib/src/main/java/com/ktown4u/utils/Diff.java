package com.ktown4u.utils;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;

import java.util.Arrays;
import java.util.List;

class Diff {
    private final String before;
    private final String after;
    private final DiffRowGenerator generator;

    private Diff(final String before, final String after) {
        this.before = before;
        this.after = after;
        generator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .mergeOriginalRevised(true)
                .inlineDiffByWord(true)
                .oldTag(f -> f ? "" : " ->")
                .newTag(f -> f ? " " : "")
                .build();
    }

    public static Diff between(final String before, final String after) {
        return new Diff(before, after);
    }

    @Override
    public String toString() {
        final List<DiffRow> rows = generator.generateDiffRows(
                Arrays.asList(before.split("\n")),
                Arrays.asList(after.split("\n"))
        );

        String result = "";

        for (final DiffRow row : rows) {
            final String s = formatted(row);
            result = result + s;
        }

        return result;
    }

    private String formatted(final DiffRow row) {
        final String s = switch (row.getTag()) {
            case EQUAL -> row.getOldLine() + "\n";
            case CHANGE -> "+ " + row.getOldLine() + "\n";
            case DELETE -> "-- " + row.getOldLine() + "\n";
            case INSERT -> "++ " + row.getNewLine() + "\n";
            default -> "";
        };
        return s;
    }
}
