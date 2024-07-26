package com.ktown4u.utils;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import org.approvaltests.core.Verifiable;

import java.util.Arrays;
import java.util.List;

class Markdown {
    private final String title;
    private final DiffRowGenerator generator;

    private Markdown(final String title) {
        this.title = title;
        generator = buildGenerator();
    }

    public static Markdown title(final String title) {
        return new Markdown(title);
    }

    public Verifiable diff(final String before, final String after) {
        final List<DiffRow> diffRows = getDiffRows(before, after);
        return new MarkdownParagraph(title, reduceToString(diffRows));
    }

    private DiffRowGenerator buildGenerator() {
        return DiffRowGenerator.create()
                .showInlineDiffs(true)
                .oldTag(f -> "")
                .newTag(f -> "")
                .build();
    }

    private List<DiffRow> getDiffRows(final String before, final String after) {
        return generator.generateDiffRows(
                Arrays.asList(before.split("\n")),
                Arrays.asList(after.split("\n"))
        );
    }

    private String reduceToString(final List<DiffRow> rows) {
        final String reduced = rows.stream()
                .map(this::formatted)
                .reduce("", String::concat);
        return "```diff\n" + reduced + "```";
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
