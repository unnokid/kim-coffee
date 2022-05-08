package com.example.kimcoffee.controller;

import com.example.kimcoffee.model.OrderItem;
import com.example.kimcoffee.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

@Controller
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final Logger logger = LoggerFactory.getLogger(OrderItemController.class);

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/orderItems")
    public String OrderItemsPage(@RequestParam("orderId") UUID orderId, Model model) {
        Optional<OrderItem> orderItems = orderItemService.getFindByOrderId(orderId);
        if (orderItems.isPresent()) {
            model.addAttribute("orderItems", orderItems.get());
        }
        return "orderItem-list";
    }
}
