package com.ecommerce.ecommerceapplication.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ecommerce.ecommerceapplication.entity.PaymentMethod;
import com.ecommerce.ecommerceapplication.entity.PaymentStatus;

public record PaymentResponse(
        Long id,
        BigDecimal amount,
        PaymentMethod method,
        PaymentStatus status,
        LocalDateTime createdAt) {
}
