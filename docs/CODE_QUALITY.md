# 코드 품질 관리

## 1. 정적 분석 도구

### SonarQube 설정

```gradle
plugins {
    id "org.sonarqube" version "3.5.0"
}

sonarqube {
    properties {
        property "sonar.projectKey", "shopping-app"
        property "sonar.sources", "src/main"
        property "sonar.tests", "src/test"
    }
}
```

## 2. 코드 리뷰 체크리스트

- [ ] 비즈니스 로직 검증
- [ ] 예외 처리 적절성
- [ ] 트랜잭션 경계 설정
- [ ] 보안 취약점 확인
- [ ] 성능 이슈 확인
- [ ] 테스트 커버리지 확인
