# user-api

Kotlin + Spring Boot 기반의 회원 관리 API

## 내용
[신규 입사자 과제 내용](https://sentbe-product.atlassian.net/wiki/spaces/S2/pages/2470084986)

# DB 환경 셋업
1. Docker-Compose를 이용한 MySQL 환경 구축
- 도커 컴포즈 파일 만든 위치로 가서 명령어 수행 
- 최초 실행 (백그라운드로 실행)
  - docker-compose.yml 파일에서 environment, ports 등의 설정을 확인하고 수정할 수 있습니다.
```bash
docker-compose up -d
```
2. MySQL 실행 및 데이터베이스 생성
```sql
CREATE DATABASE userdb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

# 실행 방법
1. application.yml DB 정보 확인 및 수정
   - src/main/resources/application.yml

2. 프로젝트 실행
```bash
./gradlew bootRun
```

3. API 테스트
- Admin 회원가입 후, DB에서 user 테이블의 role 컬럼 'ADMIN'으로 update


4. Test Case 실행


---
# Docker 명령어

- 상태 확인
```bash
docker-compose ps
```
- 중지
```bash
docker-compose down
```

---
# JWT
https://jwt.io/