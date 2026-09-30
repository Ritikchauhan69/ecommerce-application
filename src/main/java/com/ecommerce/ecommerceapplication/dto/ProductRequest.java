package com.ecommerce.ecommerceapplication.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest(
        @NotBlank String name,
        String description,
        @NotNull @DecimalMin(value = "0.01") BigDecimal price,
        @NotNull @PositiveOrZero Integer stockQuantity,
        @NotNull Long categoryId) {
}
