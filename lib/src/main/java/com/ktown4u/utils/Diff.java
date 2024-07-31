package com.ktown4u.utils;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;

import java.util.Arrays;
import java.util.List;

public class Diff {
    private final String before;
    private final String after;
    private final DiffRowGenerator generator;

    private Diff(final String before, final String after) {
        this.before = before;
        this.after = after;
        generator = buildGenerator();
    }

    public static Diff between(final String before, final String after) {
        return new Diff(before, after);
    }

    @Override
    public String toString() {
        final List<DiffRow> rows = getDiffRows();
        return reduceToString(rows);
    }

    private DiffRowGenerator buildGenerator() {
        return DiffRowGenerator.create()
                .showInlineDiffs(true)
                .mergeOriginalRevised(true)
                .inlineDiffByWord(true)
                .oldTag(f -> f ? "" : " ->")
                .newTag(f -> f ? " " : "")
                .build();
    }

    private List<DiffRow> getDiffRows() {
        return generator.generateDiffRows(
                Arrays.asList(before.split("\n")),
                Arrays.asList(after.split("\n"))
        );
    }

    private String reduceToString(final List<DiffRow> rows) {
        return rows.stream()
                .map(this::formatted)
                .reduce("", String::concat);
    }

    private String formatted(final DiffRow row) {
        final String result;
        switch (row.getTag()) {
            case EQUAL:
                result = row.getOldLine() + "\n";
                break;
            case CHANGE:
                result = "+ " + row.getOldLine() + "\n";
                break;
            case DELETE:
                result = "-- " + row.getOldLine().replace(" ->", "") + "\n";
                break;
            case INSERT:
                result = "++ " + row.getNewLine() + "\n";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }
}
