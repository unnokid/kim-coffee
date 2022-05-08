package com.example.kimcoffee.repository;

import com.example.kimcoffee.model.OrderItem;

import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository {
    Optional<OrderItem> findById(UUID orderId);
    OrderItem insert(OrderItem orderItem);
}
