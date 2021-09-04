package org.example.kafka.ecommerce.entities;

import java.math.BigDecimal;
import java.util.UUID;

public class Order {
    private final UUID userId, productId, id;
    private final BigDecimal price;

    public Order(UUID userId, UUID productId, BigDecimal price) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.productId = productId;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", id=" + id +
                ", price=" + price +
                '}';
    }
}
