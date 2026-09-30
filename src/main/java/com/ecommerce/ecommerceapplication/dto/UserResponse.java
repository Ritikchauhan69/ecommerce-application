package com.ecommerce.ecommerceapplication.dto;

import java.util.Set;

import com.ecommerce.ecommerceapplication.entity.Role;

public record UserResponse(Long id, String name, String email, Set<Role> roles) {
}
