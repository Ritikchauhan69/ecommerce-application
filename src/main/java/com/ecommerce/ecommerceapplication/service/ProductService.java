package com.ecommerce.ecommerceapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerceapplication.entity.Product;
import com.ecommerce.ecommerceapplication.entity.Category;
import com.ecommerce.ecommerceapplication.repository.CategoryRepository;
import com.ecommerce.ecommerceapplication.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product createProduct(String name, String description, java.math.BigDecimal price,
            Integer stockQuantity, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
        product.setCategory(category);
        return productRepository.save(product);
    }

    public void reduceStock(Long productId, int quantity) {
        Product product = getProductById(productId);

        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }

}
