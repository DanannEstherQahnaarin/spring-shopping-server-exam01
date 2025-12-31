package com.example.shopping.domain.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.domain.dto.OrderDto;
import com.example.shopping.domain.service.OrderService;
import com.example.shopping.global.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtTokenProvider jwtTokenProvider;

    // 헤더 토큰에서 userId 추출하는 헬퍼 메서드
    private Long getUserId(String token) {
        String tokenValue = token.substring(7); // "Bearer " 제거
        return Long.parseLong(jwtTokenProvider.getUserPk(tokenValue));
    }

    // 장바구니 담기
    @PostMapping("/cart")
    public ResponseEntity<String> addToCart(@RequestHeader("Authorization") String token,
                                            @RequestBody OrderDto.AddToCart request) {
        Long userId = getUserId(token);
        orderService.addToCart(userId, request);
        return ResponseEntity.ok("장바구니에 담겼습니다.");
    }

    // 장바구니 조회
    @GetMapping("/cart")
    public ResponseEntity<List<OrderDto.CartItemResponse>> getCart(@RequestHeader("Authorization") String token) {
        Long userId = getUserId(token);
        return ResponseEntity.ok(orderService.getCartItems(userId));
    }

    // 주문하기 (장바구니 전체)
    @PostMapping("")
    public ResponseEntity<Long> createOrder(@RequestHeader("Authorization") String token) {
        Long userId = getUserId(token);
        Long orderId = orderService.createOrder(userId);
        return ResponseEntity.ok(orderId);
    }
}