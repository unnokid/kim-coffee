package com.example.kimcoffee.service;

import com.example.kimcoffee.model.OrderItem;
import com.example.kimcoffee.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultOrderItemService implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public DefaultOrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Optional<OrderItem> getFindByOrderId(UUID orderId) {
        return orderItemRepository.findById(orderId);
    }

    @Override
    public OrderItem createOrderItem(UUID orderId, UUID productId, long price, long quantity) {
        OrderItem orderItem = new OrderItem(
                orderId,
                productId,
                price,
                quantity
        );
        return orderItemRepository.insert(orderItem);
    }
}
