package com.ktown4u.utils;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FormatLineTest {
    private final int columns = 10;
    private LineFormatter lineFormatter = new LineFormatter(columns);

    @BeforeEach
    void setUp() {
        lineFormatter = new LineFormatter(columns);
    }

    @DisplayName("name, value, columns를 입력하면 name과 value 사이에 columns만큼의 공백을 추가한 문자열을 반환한다")
    @Test
    void case0() {
        // given
        final String name = "name";
        final Object value = "value";

        // when
        final String result = lineFormatter.formatLineWithWhitespaces(name, value);

        // then
        Approvals.verify(result);
    }
}