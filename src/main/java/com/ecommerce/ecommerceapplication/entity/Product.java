package com.ecommerce.ecommerceapplication.entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @Column(name = "name", nullable = false)
    private String name;
     
    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "price",nullable = false,precision = 10,scale = 2)
    private BigDecimal price;

    @Column(name = "stock_quantity",nullable = false)
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @CreationTimestamp
@Column(name = "created_at", updatable = false)
private LocalDateTime createdAt;

@UpdateTimestamp
@Column(name = "updated_at")
private LocalDateTime updatedAt;

@Column(name = "is_deleted", nullable = false)
private boolean isDeleted = false;
}
