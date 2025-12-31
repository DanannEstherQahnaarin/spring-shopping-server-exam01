package com.example.shopping.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shopping.domain.entity.order.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}