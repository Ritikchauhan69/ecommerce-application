package com.ecommerce.ecommerceapplication.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerceapplication.entity.Cart;
import com.ecommerce.ecommerceapplication.entity.CartItem;
import com.ecommerce.ecommerceapplication.entity.Order;
import com.ecommerce.ecommerceapplication.entity.OrderItem;
import com.ecommerce.ecommerceapplication.entity.OrderStatus;
import com.ecommerce.ecommerceapplication.entity.Product;
import com.ecommerce.ecommerceapplication.entity.User;
import com.ecommerce.ecommerceapplication.repository.CartItemRepository;
import com.ecommerce.ecommerceapplication.repository.OrderItemRepository;
import com.ecommerce.ecommerceapplication.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                         CartService cartService, CartItemRepository cartItemRepository,
                         ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    @Transactional
    public Order checkout(User user) {
        Cart cart = cartService.getOrCreateCart(user);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cannot checkout an empty cart");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            productService.reduceStock(product.getId(), cartItem.getQuantity());

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(itemTotal);
        }

        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getPrice());
            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteAll(cartItems);

        return savedOrder;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

}
