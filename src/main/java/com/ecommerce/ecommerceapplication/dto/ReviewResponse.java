package com.ecommerce.ecommerceapplication.dto;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long userId,
        String userName,
        Integer rating,
        String comment,
        LocalDateTime createdAt) {
}
