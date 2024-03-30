package com.ktown4u.utils;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LineFormatterTest {
    @Test
    void line_with_no_depth() {
        LineFormatter lineFormatter = new LineFormatter(80);
        String formattedLine = lineFormatter.formatLineWithWhitespaces("name", "value");
        String expected = "name                                                                       value\n";
        assertThat(formattedLine).isEqualTo(expected);
    }

    @Test
    void line_with_depth() {
        LineFormatter lineFormatter = new LineFormatter(80);
        String formattedLine = lineFormatter.formatLineWithWhitespaces(1, "name", "value");
        String expected = "    name                                                                   value\n";
        assertThat(formattedLine).isEqualTo(expected);
    }

    @Test
    void multiple_lines_with_depth() {
        LineFormatter lineFormatter = new LineFormatter(80);
        List<IndentiedLine> lines = List.of(
                new IndentiedLine(1, "key1", "value1"),
                new IndentiedLine(1, "key2", "value2"),
                new IndentiedLine(2, "key11", "value1"),
                new IndentiedLine(2, "key12", "value2")
        );
        String formattedLine = lineFormatter.formatLineWithWhitespaces("key", null);
        for (IndentiedLine line : lines) {
            formattedLine += lineFormatter.formatLineWithWhitespaces(line.depth, line.name, line.value);
        }
        Approvals.verify(formattedLine);
    }

    record IndentiedLine(int depth, String name, String value) {
    }
}