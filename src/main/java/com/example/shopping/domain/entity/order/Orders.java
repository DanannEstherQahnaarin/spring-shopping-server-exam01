package com.example.shopping.domain.entity.order;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.example.shopping.domain.entity.BaseTimeEntity;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    @Builder.Default
    private String status = "complete"; // complete, cancel

    @Column(name = "ordered_at")
    @Builder.Default
    private LocalDateTime orderedAt = LocalDateTime.now();
}