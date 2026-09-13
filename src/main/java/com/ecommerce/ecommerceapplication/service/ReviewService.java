package com.ecommerce.ecommerceapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerceapplication.entity.Product;
import com.ecommerce.ecommerceapplication.entity.Review;
import com.ecommerce.ecommerceapplication.entity.User;
import com.ecommerce.ecommerceapplication.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    public ReviewService(ReviewRepository reviewRepository, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }

    public Review addReview(User user, Long productId, Integer rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Product product = productService.getProductById(productId);

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

}
