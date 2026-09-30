package com.ecommerce.ecommerceapplication.dto;

public record AddressResponse(
        Long id,
        String street,
        String city,
        String state,
        String postalCode,
        String country) {
}
