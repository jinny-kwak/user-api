# user-api

Kotlin + Spring Boot 기반의 회원 관리 API

## 실행 방법

1. MySQL 실행 및 데이터베이스 생성

```sql
CREATE DATABASE memberdb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. application.yml DB 정보 확인 (src/main/resources/application.yml)

3. 프로젝트 실행

```bash
./gradlew bootRun
```

4. Swagger 또는 Postman을 통해 API 테스트
