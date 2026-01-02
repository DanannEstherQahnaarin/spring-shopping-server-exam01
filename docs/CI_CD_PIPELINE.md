# CI/CD 파이프라인

## 1. GitHub Actions 예시

```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          java-version: '21'
      - name: Run tests
        run: ./gradlew test
      - name: Generate coverage report
        run: ./gradlew jacocoTestReport
      - name: Upload coverage
        uses: codecov/codecov-action@v3

  build:
    needs: test
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Build Docker image
        run: docker build -t shopping-app:${{ github.sha }} .
      - name: Push to registry
        run: docker push shopping-app:${{ github.sha }}

  deploy:
    needs: build
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    steps:
      - name: Deploy to production
        run: |
          # 배포 스크립트
```

## 2. 배포 전략

### Blue-Green 배포

- 두 개의 동일한 환경 운영
- 새 버전을 Green 환경에 배포
- 트래픽 전환 후 Blue 환경 제거

### Canary 배포

- 소수의 사용자에게만 새 버전 배포
- 모니터링 후 점진적 확대

