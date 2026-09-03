package com.ecommerce.ecommerceapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerceapplication.entity.User;
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
