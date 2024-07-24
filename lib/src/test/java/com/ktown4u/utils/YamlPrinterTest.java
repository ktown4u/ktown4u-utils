package com.ktown4u.utils;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ktown4u.utils.OrderBuilder.anOrder;
import static com.ktown4u.utils.OrderLineItemBuilder.anOrderLineItem;
import static com.ktown4u.utils.ProductBuilder.aProduct;

class YamlPrinterTest {

    private final Order order =
            anOrder()
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
    @DisplayName("print - 모든 필드를 YAML 포멧 문자열로 반환.")
    void print() {
        final String result = YamlPrinter.print(order);

        Approvals.verify(result);
    }

    @Test
    @DisplayName("printWithExclusions - 원하는 필드를 제외하고 YAML 포멧 문자열로 반환.")
    void printWithExclusions() {
        final String[] filedNamesToExclude = {"id", "description"};
        final String result = YamlPrinter.printWithExclusions(order, filedNamesToExclude);

        Approvals.verify(result);
    }

    @Test
    @DisplayName("printWithInclusions - 원하는 필드만 포함하고 YAML 포멧 문자열로 반환.")
    void printWithInclusions() {
        final String[] filedPathToInclude = {"lineItems.product.description", "lineItems.product.price"};
        final String result = YamlPrinter.printWithInclusions(order, filedPathToInclude);

        Approvals.verify(result);
    }
}