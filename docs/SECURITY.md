# 보안 고려사항

## 1. 인증/인가 보안

### JWT 토큰 보안

- Access Token: 짧은 만료 시간 (15분)
- Refresh Token: 긴 만료 시간 (7일), HTTP-only Cookie 저장
- 토큰 무효화: Redis를 활용한 블랙리스트 관리

### 비밀번호 보안

```java
// BCrypt 해싱 (강도 12)
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
String hashedPassword = encoder.encode(rawPassword);
```

### Rate Limiting

```java
@RateLimiter(name = "login", fallbackMethod = "loginFallback")
public ResponseEntity<TokenResponse> login(LoginRequest request) { ... }
```

## 2. API 보안

### 입력 검증

```java
@Valid @RequestBody OrderDto.AddToCart request
// Bean Validation 활용
@Min(1) @Max(999)
private Integer qty;
```

### SQL Injection 방지

- JPA/Hibernate 사용으로 자동 방지
- QueryDSL 사용 시 파라미터 바인딩 필수

### XSS 방지

- Spring Security 기본 설정 활용
- JSON 응답 시 자동 이스케이프

### CORS 설정

```java
@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("https://example.com"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        return new UrlBasedCorsConfigurationSource();
    }
}
```

## 3. 데이터 보안

### 민감 정보 암호화

- 비밀번호: BCrypt 해싱
- 개인정보: AES-256 암호화 (선택적)
- 로그: 민감 정보 마스킹

### 감사 로그 (Audit Log)

```java
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Orders extends BaseTimeEntity {
    @CreatedBy
    private String createdBy;
    
    @LastModifiedBy
    private String lastModifiedBy;
}
```
