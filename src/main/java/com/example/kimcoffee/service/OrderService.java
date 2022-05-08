package com.example.kimcoffee.service;

import com.example.kimcoffee.model.Email;
import com.example.kimcoffee.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Order createOrder(Email email, String address, String postcode);
    List<Order> getAllOrder();
    Optional<Order> findById(UUID orderId);
}
