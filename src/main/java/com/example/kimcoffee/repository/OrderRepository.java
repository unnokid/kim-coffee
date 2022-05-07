package com.example.kimcoffee.repository;

import com.example.kimcoffee.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    Order insert(Order order);
    Order update(Order order);
    List<Order> findAll();
    void deleteAll();
    void deleteById(UUID orderId);
}
