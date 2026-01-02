# 모니터링 및 로깅

## 1. 구조화된 로깅

### Logback 설정

```xml
<configuration>
    <appender name="JSON" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <timestamp/>
                <version/>
                <logLevel/>
                <message/>
                <mdc/>
                <stackTrace/>
            </providers>
        </encoder>
    </appender>
</configuration>
```

### 로깅 전략

- ERROR: 예외 발생, 복구 불가능한 오류
- WARN: 예상 가능한 문제, 성능 이슈
- INFO: 비즈니스 이벤트 (주문 생성, 결제 완료)
- DEBUG: 개발 환경에서만 활성화

## 2. APM (Application Performance Monitoring)

### Spring Boot Actuator

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,metrics,prometheus
  metrics:
    export:
      prometheus:
        enabled: true
```

### 모니터링 지표

- API 응답 시간 (P50, P95, P99)
- 데이터베이스 쿼리 시간
- JVM 메모리 사용량
- 트랜잭션 처리량 (TPS)

## 3. 알림 설정

### 에러 알림

- Slack/Discord 웹훅 연동
- 에러율 임계값 초과 시 알림
- 데이터베이스 연결 실패 알림

