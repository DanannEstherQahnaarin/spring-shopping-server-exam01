package com.example.shopping.domain.service;

import org.springframework.stereotype.Service;

import com.example.shopping.domain.dto.ProductDto;
import com.example.shopping.domain.entity.product.Category;
import com.example.shopping.domain.entity.product.Product;
import com.example.shopping.domain.repository.CategoryRepository;
import com.example.shopping.domain.repository.ProductRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Transactional
    public Long createCategory(ProductDto.CreateCategory request) {
        Category category = Category.builder()
                .name(request.getName())
                .build();

        return categoryRepository.save(category).getCategoryId();
    }

    @Transactional
    public Long createProduct(ProductDto.CreateProduct request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();

        Product product = Product.builder()
                .category(category)
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();

        return productRepository.save(product).getProductId();
    }

    @Transactional(readOnly = true) // 조회 전용 트랜잭션 (성능 최적화)
    public java.util.List<ProductDto.Response> getProductList() {
        return productRepository.findAllProducts();
    }
}
