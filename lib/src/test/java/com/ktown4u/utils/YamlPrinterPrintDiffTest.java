package com.ktown4u.utils;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ktown4u.utils.OrderBuilder.anOrder;

class YamlPrinterPrintDiffTest {

    @Test
    @DisplayName("printDiff - 두 객체의 차이를 YAML 포멧 문자열로 반환.")
    void printDiff() {
        final Long customer = 1L;
        final Order order1 = anOrder().id(1L).customerId(customer).build();
        final Order order2 = anOrder().id(2L).customerId(customer).build();

        final String result = YamlPrinter.printDiff(order1, order2);

        Approvals.verify(result);
    }

    @Test
    @DisplayName("printDiff - null 과의 비교가 가능하다.")
    void printDiffCanCompareNull() {
        final Order order1 = anOrder().id(1L).customerId(1L).build();
        final Order order2 = anOrder().id(2L).customerId(null).build();

        final String result = YamlPrinter.printDiff(order1, order2);

        Approvals.verify(result);
    }

    @Test
    @DisplayName("printDiff - null 끼리도 비교가 가능하다.")
    void printDiffCanCompareBothNull() {
        final Order order1 = anOrder().id(1L).customerId(null).build();
        final Order order2 = anOrder().id(2L).customerId(null).build();

        final String result1 = YamlPrinter.printDiff(order1, order2);

        Approvals.verify(result1);
    }

    // todo: depth 2 이상의 객체 비교 테스트 추가

}