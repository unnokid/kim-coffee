package com.example.kimcoffee.repository;

import com.example.kimcoffee.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order insert(Order order);
    List<Order> findAll();
    Optional<Order> findById(UUID orderId);
}
