package com.ktown4u.utils;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ktown4u.utils.OrderBuilder.anOrder;
import static com.ktown4u.utils.OrderLineItemBuilder.anOrderLineItem;
import static com.ktown4u.utils.ProductBuilder.aProduct;

class YamlWriterTest {

    private final Order order = anOrder()
            .orderLineItems(
                    anOrderLineItem()
                            .quantity(2L)
                            .product(
                                    aProduct()
                                            .name("Kenya AA Drip Coffee")
                                            .description("Bright, citrusy, with a hint of cocoa and a smooth finish.")
                                            .price("8000")
                            ),
                    anOrderLineItem()
                            .quantity(1L)
                            .product(
                                    aProduct()
                                            .name("Americano")
                                            .description("2-shot original blend Americano.")
                                            .price("5000")
                            )
            )
            .build();

    @Test
    @DisplayName("write - 모든 필드를 YAML 포멧 문자열로 반환.")
    void write() {
        final String result = YamlWriter.write(order);

        Approvals.verify(result);
    }
}