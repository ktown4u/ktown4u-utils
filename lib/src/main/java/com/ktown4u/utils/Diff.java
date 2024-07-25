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
            final String s;
            if (DiffRow.Tag.EQUAL == row.getTag()) s = row.getOldLine() + "\n";
            else if (DiffRow.Tag.CHANGE == row.getTag()) s = "+ " + row.getOldLine() + "\n";
            else if (DiffRow.Tag.DELETE == row.getTag()) s = "-- " + row.getOldLine() + "\n";
            else if (DiffRow.Tag.INSERT == row.getTag()) s = "++ " + row.getNewLine() + "\n";
            else s = "";
            result = result + s;
        }

        return result;
    }
}
