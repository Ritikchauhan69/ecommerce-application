package com.ecommerce.ecommerceapplication.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceapplication.dto.AddCartItemRequest;
import com.ecommerce.ecommerceapplication.dto.CartItemResponse;
import com.ecommerce.ecommerceapplication.dto.CartResponse;
import com.ecommerce.ecommerceapplication.dto.UpdateCartItemRequest;
import com.ecommerce.ecommerceapplication.entity.CartItem;
import com.ecommerce.ecommerceapplication.entity.User;
import com.ecommerce.ecommerceapplication.service.CartService;
import com.ecommerce.ecommerceapplication.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/{userId}/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(toCartResponse(getUserCartItems(userId)));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(
            @PathVariable Long userId,
            @Valid @RequestBody AddCartItemRequest request) {
        User user = userService.getUserById(userId);
        cartService.addItemToCart(user, request.productId(), request.quantity());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toCartResponse(cartService.getCartItems(user)));
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> updateItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        User user = userService.getUserById(userId);
        cartService.updateItemQuantity(user, itemId, request.quantity());
        return ResponseEntity.ok(toCartResponse(cartService.getCartItems(user)));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        cartService.removeItemFromCart(userService.getUserById(userId), itemId);
        return ResponseEntity.noContent().build();
    }

    private List<CartItem> getUserCartItems(Long userId) {
        return cartService.getCartItems(userService.getUserById(userId));
    }

    private CartResponse toCartResponse(List<CartItem> cartItems) {
        List<CartItemResponse> items = cartItems.stream()
                .map(item -> new CartItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity(),
                        item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))))
                .toList();

        BigDecimal total = items.stream()
                .map(CartItemResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(items, total);
    }
}
