package com.example.kimcoffee.service;

import com.example.kimcoffee.model.Email;
import com.example.kimcoffee.model.Order;
import com.example.kimcoffee.model.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);
    List<Order> getAllOrder();
}
