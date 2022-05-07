package com.example.kimcoffee.model;

import java.util.UUID;

public class OrderItem {
    private final UUID productId;
    private final Category category;
    private final long price;
    private final int count;

    public OrderItem(UUID productId, Category category, long price, int count) {
        this.productId = productId;
        this.category = category;
        this.price = price;
        this.count = count;
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

    public int getCount() {
        return count;
    }
}
