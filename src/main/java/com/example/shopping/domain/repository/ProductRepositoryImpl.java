package com.example.shopping.domain.repository;

import static com.example.shopping.domain.entity.product.QProduct.product;
import static com.example.shopping.domain.entity.product.QCategory.category;
import java.util.List;

import com.example.shopping.domain.dto.ProductDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryC {

    private JPAQueryFactory queryFactory;

    @Override
    public List<ProductDto.Response> findAllProducts() {
        return queryFactory
                .select(Projections.fields(ProductDto.Response.class,
                        product.productId,
                        category.name.as("categoryName"),
                        product.name,
                        product.price,
                        product.stock))
                .from(product)
                .join(product.category, category)
                .orderBy(product.productId.desc())
                .fetch();
    }

}
