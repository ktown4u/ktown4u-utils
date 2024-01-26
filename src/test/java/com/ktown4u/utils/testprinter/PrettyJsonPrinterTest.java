package com.ktown4u.utils.testprinter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ktown4u.utils.testprinter.OrderLineItemBuilder.anOrderLineItem;
import static com.ktown4u.utils.testprinter.ProductBuilder.aProduct;
import static java.util.stream.Collectors.joining;

public class PrettyJsonPrinterTest {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE); // Disable auto-detection for all methods
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); // Enable visibility for fields

        mapper.registerModule(javaTimeModule);
    }

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
                .filter(line -> !line.contains("id"))
                .filter(line -> !line.contains("description"))
                .collect(joining("\n"))
        );
    }

    private List<String> prettyPrint(Object object) {
        try {
            return Arrays.asList(
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object)
                            .split("\n"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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