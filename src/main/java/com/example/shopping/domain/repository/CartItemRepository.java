package com.example.shopping.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shopping.domain.entity.order.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);
    List<CartItem> findAllByCart_CartId(Long cartId);
    void deleteAllByCart_CartId(Long cartId);
}