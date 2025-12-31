package com.example.shopping.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shopping.domain.entity.product.Category;
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
