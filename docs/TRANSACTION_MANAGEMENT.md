# 트랜잭션 관리 전략

## 1. 트랜잭션 격리 수준

### 기본 전략

- 읽기 전용: `READ_COMMITTED` (기본값)
- 쓰기 작업: `REPEATABLE_READ` (선택적)

### 데드락 방지

```java
@Transactional(isolation = Isolation.READ_COMMITTED)
public void createOrder(Long userId) {
    // 비관적 락 (Pessimistic Lock)
    Product product = productRepository.findByIdWithLock(productId)
        .orElseThrow(...);
    
    // 낙관적 락 (Optimistic Lock)
    @Version
    private Long version;
}
```

## 2. 분산 트랜잭션

### Saga 패턴 (마이크로서비스 환경)

- 주문 생성 → 재고 차감 → 결제 처리
- 각 단계별 보상 트랜잭션 구현

### 이벤트 기반 아키텍처

```java
@TransactionalEventListener
public void handleOrderCreated(OrderCreatedEvent event) {
    // 비동기 이벤트 처리
    inventoryService.decreaseStock(event.getOrderItems());
}
```

