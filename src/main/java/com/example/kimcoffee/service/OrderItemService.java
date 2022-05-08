package com.example.kimcoffee.service;

import com.example.kimcoffee.model.OrderItem;

import java.util.Optional;
import java.util.UUID;

public interface OrderItemService {
    Optional<OrderItem> getFindByOrderId(UUID orderId);
    OrderItem createOrderItem(UUID orderId,UUID productId, long price, long quantity);
}
