package com.ecommerce.ecommerceapplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceapplication.dto.PaymentRequest;
import com.ecommerce.ecommerceapplication.dto.PaymentResponse;
import com.ecommerce.ecommerceapplication.entity.Order;
import com.ecommerce.ecommerceapplication.entity.Payment;
import com.ecommerce.ecommerceapplication.service.OrderService;
import com.ecommerce.ecommerceapplication.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/{userId}/orders/{orderId}/payment")
public class PaymentController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public PaymentController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> recordPayment(
            @PathVariable Long userId,
            @PathVariable Long orderId,
            @Valid @RequestBody PaymentRequest request) {
        Order order = orderService.getOrderForUser(userId, orderId);
        Payment payment = paymentService.recordPayment(order, order.getTotalAmount(), request.method());
        return ResponseEntity.status(HttpStatus.CREATED).body(toPaymentResponse(payment));
    }

    @GetMapping
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable Long userId,
            @PathVariable Long orderId) {
        orderService.getOrderForUser(userId, orderId);
        return ResponseEntity.ok(toPaymentResponse(paymentService.getPaymentByOrderId(orderId)));
    }

    private PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(payment.getId(), payment.getAmount(), payment.getPaymentMethod(),
                payment.getPaymentStatus(), payment.getCreatedAt());
    }
}
