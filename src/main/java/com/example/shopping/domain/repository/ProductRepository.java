package com.example.shopping.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.domain.entity.product.Product;
public interface ProductRepository extends JpaRepository<Product, Long> , ProductRepositoryC {
    
}
