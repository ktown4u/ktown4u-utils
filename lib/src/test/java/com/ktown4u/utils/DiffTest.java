package com.ktown4u.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DiffTest {
    @Test
    @DisplayName("두 문자열을 비교한다")
    void diff() {
        final String before = before();
        final String after = after();

        final String result = new Diff(before, after).print();

        assertThat(result).isNotBlank();
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