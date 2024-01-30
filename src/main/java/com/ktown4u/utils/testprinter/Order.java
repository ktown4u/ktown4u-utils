package com.ktown4u.utils.testprinter;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Order {
    private Long id;
    private List<OrderLineItem> lineItems;
}

@Data
class OrderLineItem {
    private Long id;
    private Long quantity;
    private Product product;
}

@Data
class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}

