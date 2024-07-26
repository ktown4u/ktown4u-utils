package com.ktown4u.utils;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;

import java.util.Arrays;
import java.util.List;

public class GitDiff {
    private final String before;
    private final String after;
    private final DiffRowGenerator generator;

    private GitDiff(final String before, final String after) {
        this.before = before;
        this.after = after;
        generator = buildGenerator();
    }

    public static GitDiff between(final String before, final String after) {
        return new GitDiff(before, after);
    }

    @Override
    public String toString() {
        final List<DiffRow> rows = getDiffRows();
        final String result = reduceToString(rows);
        return "```diff\n" + result + "```";
    }

    private DiffRowGenerator buildGenerator() {
        return DiffRowGenerator.create()
                .showInlineDiffs(true)
                .oldTag(f -> "")
                .newTag(f -> "")
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
        return switch (row.getTag()) {
            case EQUAL -> row.getOldLine() + "\n";
            case CHANGE -> "- " + row.getOldLine() + "\n" + "+ " + row.getNewLine() + "\n";
            case DELETE -> "- " + row.getOldLine() + "\n";
            case INSERT -> "+ " + row.getNewLine() + "\n";
            default -> "";
        };
    }
}
