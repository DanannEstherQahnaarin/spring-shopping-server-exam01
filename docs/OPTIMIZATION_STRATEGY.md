# 성능 최적화 전략

## 1. 데이터베이스 최적화

### 인덱싱 전략

```sql
-- 주문 조회 최적화를 위한 복합 인덱스
CREATE INDEX idx_orders_user_status ON orders(user_id, status);
CREATE INDEX idx_orders_created_at ON orders(created_at DESC);

-- 장바구니 조회 최적화
CREATE INDEX idx_cart_items_cart_product ON cart_items(cart_id, product_id);

-- 주문 항목 조회 최적화
CREATE INDEX idx_order_items_order ON order_items(order_id);
```

### 쿼리 최적화

- N+1 문제 해결: `@EntityGraph`, `fetch join` 활용
- 배치 처리: 대량 데이터 처리 시 `@BatchSize` 활용
- 읽기 전용 쿼리: `@Transactional(readOnly = true)` 활용

### 연결 풀 설정

```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
```

## 2. JPA 성능 최적화

### 지연 로딩 vs 즉시 로딩

- 기본적으로 `@OneToMany`, `@ManyToMany`는 `LAZY` 로딩
- 필요한 경우에만 `@EntityGraph` 또는 `fetch join` 사용

### 캐싱 전략

```java
// 2차 캐시 활성화 (Hibernate)
@Cacheable("products")
@Entity
public class Product { ... }

// Spring Cache (Redis 연동)
@Cacheable(value = "categories", key = "#root.method.name")
public List<Category> findAllCategories() { ... }
```

### 배치 처리

```java
@Modifying
@Query("UPDATE Product p SET p.stock = p.stock - :quantity WHERE p.id = :id")
void decreaseStock(@Param("id") Long id, @Param("quantity") int quantity);
```

## 3. API 응답 최적화

### 페이지네이션

```java
public Page<OrderDto.OrderResponse> getOrderList(Long userId, Pageable pageable) {
    return ordersRepository.findByUserId(userId, pageable)
        .map(this::toOrderResponse);
}
```

### DTO 변환 최적화

- MapStruct 또는 ModelMapper 사용으로 리플렉션 오버헤드 감소
- 필요한 필드만 선택적으로 조회 (Projection 활용)
