package com.example.shopping.domain.entity.user;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_auth")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserAuth {

    @Id
    @Column(name = "user_id")
    private Long userId;

    // User 테이블과 1:1 식별 관계 (PK이자 FK)
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Column(name = "password_updated_at")
    private LocalDateTime passwordUpdatedAt;

    @Column(name = "failed_login_count")
    @Builder.Default
    private int failedLoginCount = 0;
}
