package com.example.shopping.domain.dto;

import lombok.Data;

public class OrderDto {

    @Data
    public static class AddToCart {
        private Long productId;
        private Integer qty;
    }

    @Data
    public static class CartItemResponse {
        private Long cartItemId;
        private Long productId;
        private String productName;
        private Integer price;
        private Integer qty;
    }
}