# 테스트 전략

## 1. 테스트 피라미드

```text
        /\
       /E2E\          (10%) - 통합 E2E 테스트
      /------\
     /Integration\    (20%) - 통합 테스트
    /------------\
   /   Unit Test  \   (70%) - 단위 테스트
  /----------------\
```

## 2. 단위 테스트

### Service 계층 테스트

```java
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrdersRepository ordersRepository;
    
    @InjectMocks
    private OrderService orderService;
    
    @Test
    void 주문_생성_성공() {
        // given
        when(ordersRepository.save(any())).thenReturn(order);
        
        // when
        Long orderId = orderService.createOrder(userId);
        
        // then
        assertThat(orderId).isNotNull();
        verify(ordersRepository).save(any());
    }
}
```

## 3. 통합 테스트

### @SpringBootTest 활용

```java
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void 장바구니_담기_통합_테스트() throws Exception {
        mockMvc.perform(post("/api/orders/cart/add")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }
}
```

## 4. 테스트 커버리지

### JaCoCo 설정

```gradle
plugins {
    id 'jacoco'
}

jacoco {
    toolVersion = "0.8.8"
}

test {
    finalizedBy jacocoTestReport
}

jacocoTestReport {
    reports {
        xml.required = true
        html.required = true
    }
}
```

### 목표 커버리지

- 전체: 80% 이상
- Service 계층: 90% 이상
- Repository 계층: 70% 이상
