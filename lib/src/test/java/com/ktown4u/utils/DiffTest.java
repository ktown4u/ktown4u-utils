package com.ktown4u.utils;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ktown4u.utils.OrderBuilder.anOrder;
import static com.ktown4u.utils.OrderLineItemBuilder.anOrderLineItem;
import static com.ktown4u.utils.ProductBuilder.aProduct;

public class DiffTest {
    @Test
    @DisplayName("두 문자열을 비교한다")
    void diff() {
        final String before = before();
        final String after = after();

        Approvals.verify(Diff.between(before, after));
    }

    @Test
    @DisplayName("두 객체를 비교한다")
    void git_diff() {
        final Order before = beforeOrder();
        final Order after = afterOrder();

        Approvals.verify(
                Markdown.title("두 객체를 비교한다.")
                        .description("두 객체를 비교하여 markdown diff 포맷으로 차이를 확인한다.")
                        .excludingFields("id", "description")
                        .diff(before, after));
    }

    private Order beforeOrder() {
        return anOrder()
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
    }

    private Order afterOrder() {
        return anOrder()
                .orderLineItems(
                        anOrderLineItem()
                                .quantity(3L)
                                .product(
                                        aProduct()
                                                .name("latte")
                                                .price("3000")
                                ),
                        anOrderLineItem()
                                .quantity(1L)
                                .product(
                                        aProduct()
                                                .name("Learning Spring Boot 3.0")
                                                .price("30000")
                                )
                )
                .build();
    }

    private String after() {
        return """
                totalPrice: 100
                totalDiscountPrice: 10
                finalPrice: 90
                mileageToBeEarned: 0
                totalQty: 2
                currency: "USD"
                tube:
                  name: ""
                  price: 0
                  qty: 0
                  active: false
                items:
                - productId:
                    shopNo: 164
                    productNo: 1
                    bundleNo: 2
                    fanClubProductNo: 3
                    eventNo: 4
                  qty: 2
                  name: "name"
                  image: "image"
                  retailPrice: 100
                  sellPrice: 90
                  artist: "artist"
                  brand: "brand"
                  saleStatusCode: "ON_SALE"
                  isAdultOnly: true
                  additionalItems: []
                """;
    }

    private String before() {
        return """
                totalPrice: 0
                totalDiscountPrice: 0
                finalPrice: 0
                mileageToBeEarned: 0
                totalQty: 0
                currency: "USD"
                tube:
                  name: ""
                  price: 0
                  qty: 0
                  active: false
                items: []
                """;
    }

}
