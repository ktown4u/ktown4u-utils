package com.ktown4u.utils;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static com.ktown4u.utils.OrderBuilder.anOrder;
import static com.ktown4u.utils.OrderLineItemBuilder.anOrderLineItem;
import static com.ktown4u.utils.PrettyJsonPrinter.outLinesIncluding;
import static com.ktown4u.utils.ProductBuilder.aProduct;
import static java.util.stream.Collectors.joining;

class PrettyJsonPrinterTest {
    @Test
    public void test() {
        Order order = anOrder()
                .orderLineItems(
                        anOrderLineItem()
                                .quantity(2L)
                                .product(
                                        aProduct()
                                                .name("coffee")
                                                .price("1000")
                                ),
                        anOrderLineItem()
                                .quantity(1L)
                                .product(
                                        aProduct()
                                                .name("Learning Spring Boot 2.0")
                                                .price("30000")
                                )
                )
                .build();

        Approvals.verify(PrettyJsonPrinter.prettyPrint(order).stream()
                .filter(outLinesIncluding("id"))
                .filter(outLinesIncluding("description"))
                .collect(joining("\n"))
        );
    }
}
