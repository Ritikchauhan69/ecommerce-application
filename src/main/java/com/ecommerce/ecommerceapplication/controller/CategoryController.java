package com.ecommerce.ecommerceapplication.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceapplication.dto.CategoryRequest;
import com.ecommerce.ecommerceapplication.dto.CategoryResponse;
import com.ecommerce.ecommerceapplication.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories().stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .toList());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        var category = categoryService.createCategory(request.name());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CategoryResponse(category.getId(), category.getName()));
    }
}
