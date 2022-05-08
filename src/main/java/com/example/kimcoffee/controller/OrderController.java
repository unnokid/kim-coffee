package com.example.kimcoffee.controller;

import com.example.kimcoffee.model.Email;
import com.example.kimcoffee.model.Order;
import com.example.kimcoffee.model.Product;
import com.example.kimcoffee.service.OrderItemService;
import com.example.kimcoffee.service.OrderService;
import com.example.kimcoffee.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final ProductService productService;

    public OrderController(OrderService orderService, OrderItemService orderItemService, ProductService productService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.productService = productService;
    }

    @GetMapping("/orders")
    public String orderPage(Model model) {
        List<Order> orders = orderService.getAllOrder();
        model.addAttribute("orders", orders);
        return "order-list";
    }

    @GetMapping("/orders/new")
    public String newOrderPage() {
        return "new-order";
    }

    @PostMapping("/orders")
    public String newOrder(Model model, CreateOrderRequest createOrderRequest) {
        Order order = orderService.createOrder(
                new Email(createOrderRequest.getEmail()),
                createOrderRequest.getAddress(),
                createOrderRequest.getPostcode());
        Optional<Product> product = productService.getFindById(createOrderRequest.getProductId());
        orderItemService.createOrderItem(
                order.getOrderId(),
                createOrderRequest.getProductId(),
                product.get().getPrice(),
                createOrderRequest.getQuantity());
        return "redirect:/orders";
    }
}
