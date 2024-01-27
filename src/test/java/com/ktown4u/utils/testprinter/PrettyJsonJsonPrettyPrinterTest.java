package com.ktown4u.utils.testprinter;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.ktown4u.utils.testprinter.JsonPrettyPrinter.prettyPrint;
import static com.ktown4u.utils.testprinter.OrderLineItemBuilder.anOrderLineItem;
import static com.ktown4u.utils.testprinter.ProductBuilder.aProduct;
import static java.util.stream.Collectors.joining;

public class PrettyJsonJsonPrettyPrinterTest {
    @Test
    public void test() {
        Order order = OrderBuilder.anOrder()
                .orderLineItems(
                        anOrderLineItem()
                                .quantity(2L)
                                .product(aProduct()
                                        .name("coffee")
                                        .price("1000")
                                ),
                        anOrderLineItem()
                                .quantity(1L)
                                .product(aProduct()
                                        .name("Learning Spring Boot 2.0")
                                        .price("30000")
                                )
                )
                .build();

        Approvals.verify(prettyPrint(order).stream()
                .filter(outLinesIncluding("id"))
                .filter(outLinesIncluding("description"))
                .collect(joining("\n"))
        );
    }

    private static Predicate<String> outLinesIncluding(String id) {
        return line -> !line.contains(id);
    }
}

class OrderBuilder {
    private Long id;
    private List<OrderLineItem> lineItems = new ArrayList<>();

    public OrderBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder orderLineItems(OrderLineItemBuilder... lineItemBuilders) {
        for (OrderLineItemBuilder lineItemBuilder : lineItemBuilders) {
            this.lineItems.add(lineItemBuilder.build());
        }
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setId(id);
        order.setLineItems(lineItems);
        return order;
    }

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }
}

class OrderLineItemBuilder {
    private Long id;
    private Long quantity;
    private Product product;

    public OrderLineItemBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public OrderLineItemBuilder quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderLineItemBuilder product(ProductBuilder productBuilder) {
        this.product = productBuilder.build();
        return this;
    }

    public OrderLineItem build() {
        OrderLineItem lineItem = new OrderLineItem();
        lineItem.setId(id);
        lineItem.setQuantity(quantity);
        lineItem.setProduct(product);
        return lineItem;
    }

    public static OrderLineItemBuilder anOrderLineItem() {
        return new OrderLineItemBuilder();
    }
}

class ProductBuilder {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;


    public ProductBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder price(String price) {
        this.price = new BigDecimal(price);
        return this;
    }

    public Product build() {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }
}