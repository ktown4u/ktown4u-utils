package com.ktown4u.utils;

import org.approvaltests.core.Options;
import org.approvaltests.core.Verifiable;
import org.approvaltests.core.VerifyParameters;

public class MarkdownParagraph implements Verifiable {
    private final String title;
    private final String description;
    private final String diff;

    public MarkdownParagraph(final String title, final String description, final String diff) {
        this.title = title;
        this.description = description;
        this.diff = diff;
    }

    @Override
    public VerifyParameters getVerifyParameters(final Options options) {
        return new VerifyParameters(options.forFile().withExtension(".md"));
    }

    @Override
    public String toString() {
        return String.format("# %s\n%s\n```diff\n%s\n```", title, description, diff);
    }
}
