package com.example.shopping.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shopping.domain.entity.order.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}