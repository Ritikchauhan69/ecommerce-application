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

import com.ecommerce.ecommerceapplication.dto.ReviewRequest;
import com.ecommerce.ecommerceapplication.dto.ReviewResponse;
import com.ecommerce.ecommerceapplication.entity.Review;
import com.ecommerce.ecommerceapplication.service.ReviewService;
import com.ecommerce.ecommerceapplication.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products/{productId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId).stream()
                .map(this::toReviewResponse)
                .toList());
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<ReviewResponse> addReview(
            @PathVariable Long productId,
            @PathVariable Long userId,
            @Valid @RequestBody ReviewRequest request) {
        Review review = reviewService.addReview(userService.getUserById(userId), productId, request.rating(),
                request.comment());
        return ResponseEntity.status(HttpStatus.CREATED).body(toReviewResponse(review));
    }

    private ReviewResponse toReviewResponse(Review review) {
        return new ReviewResponse(review.getId(), review.getUser().getId(), review.getUser().getName(),
                review.getRating(), review.getComment(), review.getCreatedAt());
    }
}
