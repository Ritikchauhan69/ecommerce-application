package com.ecommerce.ecommerceapplication.dto;

import com.ecommerce.ecommerceapplication.entity.PaymentMethod;

import jakarta.validation.constraints.NotNull;

public record PaymentRequest(@NotNull PaymentMethod method) {
}
