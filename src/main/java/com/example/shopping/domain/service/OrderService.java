package com.example.shopping.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shopping.domain.dto.OrderDto;
import com.example.shopping.domain.entity.order.Cart;
import com.example.shopping.domain.entity.order.CartItem;
import com.example.shopping.domain.entity.order.OrderItem;
import com.example.shopping.domain.entity.order.Orders;
import com.example.shopping.domain.entity.product.Product;
import com.example.shopping.domain.repository.CartItemRepository;
import com.example.shopping.domain.repository.CartRepository;
import com.example.shopping.domain.repository.OrderItemRepository;
import com.example.shopping.domain.repository.OrdersRepository;
import com.example.shopping.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository;

    // 장바구니에 상품 담기
    @Transactional
    public void addToCart(Long userId, OrderDto.AddToCart request) {
        // 1. 유저의 장바구니 조회 (없으면 생성)
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.builder().userId(userId).build()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));

        // 2. 이미 장바구니에 있는 상품인지 확인
        CartItem cartItem = cartItemRepository
                .findByCart_CartIdAndProduct_ProductId(cart.getCartId(), product.getProductId())
                .orElse(null);

        if (cartItem != null) {
            // 이미 있으면 수량 증가
            cartItem.addQty(request.getQty());
        } else {
            // 없으면 새로 생성
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .qty(request.getQty())
                    .build();
            cartItemRepository.save(cartItem);
        }
    }

    // 장바구니 목록 조회
    @Transactional(readOnly = true)
    public List<OrderDto.CartItemResponse> getCartItems(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart == null)
            return List.of();

        return cartItemRepository.findAllByCart_CartId(cart.getCartId()).stream()
                .map(item -> {
                    OrderDto.CartItemResponse res = new OrderDto.CartItemResponse();
                    res.setCartItemId(item.getCartItemId());
                    res.setProductId(item.getProduct().getProductId());
                    res.setProductName(item.getProduct().getName());
                    res.setPrice(item.getProduct().getPrice());
                    res.setQty(item.getQty());
                    return res;
                }).collect(Collectors.toList());
    }

    // 주문하기 (장바구니 상품 전체 주문) - 트랜잭션 필수
    @Transactional
    public Long createOrder(Long userId) {
        // 1. 장바구니 조회
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("장바구니가 비어있습니다."));

        List<CartItem> cartItems = cartItemRepository.findAllByCart_CartId(cart.getCartId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("주문할 상품이 없습니다.");
        }

        // 2. 주문 생성
        Orders order = Orders.builder()
                .userId(userId)
                .status("complete")
                .build();
        ordersRepository.save(order);

        // 3. 주문 상품 생성 및 재고 차감 (핵심)
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            // 재고 차감 (실패 시 예외 발생 -> 전체 롤백)
            product.removeStock(cartItem.getQty());

            // 주문 상세 저장
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .qty(cartItem.getQty())
                    .priceAtOrder(product.getPrice())
                    .build();
            orderItemRepository.save(orderItem);
        }

        // 4. 장바구니 비우기
        cartItemRepository.deleteAllByCart_CartId(cart.getCartId());

        return order.getOrderId();
    }
}