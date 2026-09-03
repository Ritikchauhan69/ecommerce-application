package com.ecommerce.ecommerceapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerceapplication.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
