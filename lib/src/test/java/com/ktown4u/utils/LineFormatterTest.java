package com.ktown4u.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LineFormatterTest {
    @Test
    void line_with_no_depth() {
        LineFormatter lineFormatter = new LineFormatter(80);
        String formattedLine = lineFormatter.formatLineWithWhitespaces("name", "value");
        String expected = "name                                                                       value\n";
        assertThat(formattedLine).isEqualTo(expected);
    }
}