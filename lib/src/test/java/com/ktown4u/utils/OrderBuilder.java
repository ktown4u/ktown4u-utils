package com.ktown4u.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class OrderBuilder {
    private Long id;
    private Long customerId;
    private List<OrderLineItem> lineItems = new ArrayList<>();

    public OrderBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder customerId(Long customerId) {
        this.customerId = customerId;
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
        order.setCustomerId(customerId);
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

    public ProductBuilder description(final String description) {
        this.description = description;
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
