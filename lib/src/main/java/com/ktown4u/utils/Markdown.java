package com.ktown4u.utils;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import org.approvaltests.core.Verifiable;

import java.util.Arrays;
import java.util.List;

class Markdown {
    private final String title;
    private final DiffRowGenerator generator;
    private String description = "";
    private String[] fieldsToIgnore = {};

    private Markdown(final String title) {
        this.title = title;
        generator = buildGenerator();
    }

    public static Markdown title(final String title) {
        return new Markdown(title);
    }

    public Markdown description(final String description) {
        this.description = description;
        return this;
    }

    public Markdown excludingFields(final String... fields) {
        fieldsToIgnore = fields;
        return this;
    }

    public Verifiable diff(final Object before, final Object after) {
        final List<DiffRow> diffRows = getDiffRows(format(before), format(after));
        return new MarkdownParagraph(title, description, reduceToString(diffRows));
    }

    private String format(final Object before) {
        return YamlPrinter.printWithExclusions(before, fieldsToIgnore).replaceAll("(?m)^- ", "* ");
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
                result = "- " + row.getOldLine() + "\n" + "+ " + row.getNewLine() + "\n";
                break;
            case DELETE:
                result = "- " + row.getOldLine() + "\n";
                break;
            case INSERT:
                result = "+ " + row.getNewLine() + "\n";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }
}
