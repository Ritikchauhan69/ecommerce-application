package com.ecommerce.ecommerceapplication.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceapplication.dto.OrderItemResponse;
import com.ecommerce.ecommerceapplication.dto.OrderResponse;
import com.ecommerce.ecommerceapplication.entity.Order;
import com.ecommerce.ecommerceapplication.entity.OrderItem;
import com.ecommerce.ecommerceapplication.entity.User;
import com.ecommerce.ecommerceapplication.service.OrderService;
import com.ecommerce.ecommerceapplication.service.UserService;

@RestController
@RequestMapping("/api/users/{userId}/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        Order order = orderService.checkout(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toOrderResponse(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable Long userId) {
        userService.getUserById(userId);
        List<OrderResponse> orders = orderService.getOrdersByUser(userId).stream()
                .map(this::toOrderResponse)
                .toList();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(toOrderResponse(orderService.getOrderForUser(userId, orderId)));
    }

    private OrderResponse toOrderResponse(Order order) {
        List<OrderItemResponse> items = orderService.getOrderItems(order.getId()).stream()
                .map(this::toOrderItemResponse)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                items);
    }

    private OrderItemResponse toOrderItemResponse(OrderItem item) {
        BigDecimal subtotal = item.getPriceAtPurchase()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return new OrderItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPriceAtPurchase(),
                subtotal);
    }
}
