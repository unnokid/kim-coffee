package com.example.kimcoffee.model;

import java.util.UUID;

public class OrderItem {
    private final UUID orderId;
    private final UUID productId;
    private final long price;
    private final long quantity;

    public OrderItem(UUID orderId, UUID productId, long price, long quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
