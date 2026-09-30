package com.ecommerce.ecommerceapplication.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceapplication.dto.AddressRequest;
import com.ecommerce.ecommerceapplication.dto.AddressResponse;
import com.ecommerce.ecommerceapplication.entity.Address;
import com.ecommerce.ecommerceapplication.entity.User;
import com.ecommerce.ecommerceapplication.service.AddressService;
import com.ecommerce.ecommerceapplication.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/{userId}/addresses")
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddresses(@PathVariable Long userId) {
        userService.getUserById(userId);
        return ResponseEntity.ok(addressService.getAddressesByUser(userId).stream()
                .map(this::toAddressResponse)
                .toList());
    }

    @PostMapping
    public ResponseEntity<AddressResponse> addAddress(
            @PathVariable Long userId,
            @Valid @RequestBody AddressRequest request) {
        User user = userService.getUserById(userId);
        Address address = addressService.addAddress(user, request.street(), request.city(), request.state(),
                request.postalCode(), request.country());
        return ResponseEntity.status(HttpStatus.CREATED).body(toAddressResponse(address));
    }

    private AddressResponse toAddressResponse(Address address) {
        return new AddressResponse(address.getId(), address.getStreet(), address.getCity(), address.getState(),
                address.getPostalCode(), address.getCountry());
    }
}
