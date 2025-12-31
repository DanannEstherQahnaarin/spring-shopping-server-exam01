package com.example.shopping.domain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.domain.dto.ProductDto;
import com.example.shopping.domain.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private ProductService productService;

    // 카테고리 등록
    @PostMapping("/category")
    public ResponseEntity<Long> createCategory(@RequestBody @Valid ProductDto.CreateCategory request) {
        Long id = productService.createCategory(request);
        return ResponseEntity.ok(id);
    }

    // 상품 등록
    @PostMapping("/")
    public ResponseEntity<Long> createProduct(@RequestBody @Valid ProductDto.CreateProduct request) {
        Long id = productService.createProduct(request);
        return ResponseEntity.ok(id);
    }
}
