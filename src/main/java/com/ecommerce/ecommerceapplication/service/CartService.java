package com.ecommerce.ecommerceapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerceapplication.entity.Cart;
import com.ecommerce.ecommerceapplication.entity.CartItem;
import com.ecommerce.ecommerceapplication.entity.Product;
import com.ecommerce.ecommerceapplication.entity.User;
import com.ecommerce.ecommerceapplication.repository.CartItemRepository;
import com.ecommerce.ecommerceapplication.repository.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    public void addItemToCart(User user, Long productId, int quantity) {
        Cart cart = getOrCreateCart(user);
        Product product = productService.getProductById(productId);

        List<CartItem> existingItems = cartItemRepository.findByCartId(cart.getId());

        for (CartItem item : existingItems) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                cartItemRepository.save(item);
                return;
            }
        }

        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        cartItemRepository.save(newItem);
    }

}
