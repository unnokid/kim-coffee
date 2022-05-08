package com.example.kimcoffee.service;

import com.example.kimcoffee.model.Email;
import com.example.kimcoffee.model.Order;
import com.example.kimcoffee.model.OrderItem;
import com.example.kimcoffee.model.OrderStatus;
import com.example.kimcoffee.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Email email, String address, String postcode) {
        Order order = new Order(
                UUID.randomUUID(),
                email,
                address,
                postcode,
                OrderStatus.ACCEPTED,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        return orderRepository.insert(order);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        return orderRepository.findById(orderId);
    }
}
