package com.example.shopping.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_code", unique = true, length = 50)
    private String roleCode; // e.g., ROLE_USER, ROLE_ADMIN

    @Column(name = "role_name", length = 100)
    private String roleName;

    @Column(name = "role_type", length = 20)
    private String roleType;
}