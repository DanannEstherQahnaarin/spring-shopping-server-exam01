package com.example.shopping.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class ProductDto {
    @Data
    public static class CreateCategory {
        @NotBlank
        private String name;
    }

    @Data
    public static class CreateProduct {
        @NotNull
        private Long categoryId;

        @NotBlank
        private String name;

        @NotNull @Min(0)
        private Integer price;

        @NotNull @Min(0)
        private Integer stock;
    }

    @Data
    public static class Response{
        private Long productId;
        private String categoryName;
        private String name;
        private Integer price;
        private Integer stock;
    }
}
