package com.example.kimcoffee.model;

import java.util.UUID;

public class OrderItem {
    private final UUID productId;
    private final Category category;
    private final long price;
    private final long quantity;

    public OrderItem(UUID productId, Category category, long price, long quantity) {
        this.productId = productId;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Category getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }
}
