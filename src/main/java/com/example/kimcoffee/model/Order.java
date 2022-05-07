package com.example.kimcoffee.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private final Email email;
    private String address;
    private String postcode;
    private OrderStatus orderStatus;
    private final LocalDateTime createAt;
    private LocalDateTime updateAt;
    private final List<OrderItem> orderItems;

    public Order(UUID orderId, Email email, String address, String postcode, OrderStatus orderStatus, LocalDateTime createAt, LocalDateTime updateAt, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderStatus = orderStatus;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.orderItems = orderItems;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Email getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", email=" + email +
                ", address='" + address + '\'' +
                ", postcode='" + postcode + '\'' +
                ", orderStatus=" + orderStatus +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", orderItems=" + orderItems +
                '}';
    }
}
