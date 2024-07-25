package com.ktown4u.utils;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;

import java.util.Arrays;
import java.util.List;

class Diff {
    private final String before;
    private final String after;

    private Diff(final String before, final String after) {
        this.before = before;
        this.after = after;
    }

    public static Diff between(final String before, final String after) {
        return new Diff(before, after);
    }

    public String print() {
        final DiffRowGenerator generator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .mergeOriginalRevised(true)
                .inlineDiffByWord(true)
                .oldTag(f -> f ? "" : " ->")
                .newTag(f -> f ? " " : "")
                .build();

        final List<DiffRow> rows = generator.generateDiffRows(
                Arrays.asList(before.split("\n")),
                Arrays.asList(after.split("\n"))
        );

        String result = "";

        for (final DiffRow row : rows) {
            if (DiffRow.Tag.EQUAL == row.getTag()) result += row.getOldLine() + "\n";
            if (DiffRow.Tag.CHANGE == row.getTag()) result += "+ " + row.getOldLine() + "\n";
            if (DiffRow.Tag.DELETE == row.getTag()) result += "-- " + row.getOldLine() + "\n";
            if (DiffRow.Tag.INSERT == row.getTag()) result += "++ " + row.getNewLine() + "\n";
        }

        return result;
    }
}
