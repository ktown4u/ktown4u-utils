package com.ktown4u.utils;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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

    private class Diff {
        private final String before;
        private final String after;

        public Diff(final String before, final String after) {
            this.before = before;
            this.after = after;
        }

        public String print() {
            final DiffRowGenerator generator = DiffRowGenerator.create()
                    .showInlineDiffs(true)
                    .mergeOriginalRevised(true)
                    .inlineDiffByWord(true)
                    .oldTag(f -> "")
                    .newTag(f -> f ? " -> " : "")
                    .build();

            final List<DiffRow> rows = generator.generateDiffRows(
                    Arrays.asList(before.split("\n")),
                    Arrays.asList(after.split("\n"))
            );

            String result = "";

            for (final DiffRow row : rows) {
                if (DiffRow.Tag.EQUAL == row.getTag()) result += row.getOldLine() + "\n";
                if (DiffRow.Tag.CHANGE == row.getTag()) result += "+ " + row.getOldLine() + "\n";
                if (DiffRow.Tag.DELETE == row.getTag()) result += "-- " + row.getOldLine() + "\n";
                if (DiffRow.Tag.INSERT == row.getTag()) result += "++ " + row.getNewLine() + "\n";
            }

            return result;
        }
    }
}
