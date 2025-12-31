package com.example.shopping.domain.repository;

import java.util.List;

import com.example.shopping.domain.dto.ProductDto;

public interface ProductRepositoryC {
    List<ProductDto.Response> findAllProducts();
}
