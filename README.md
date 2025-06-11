# user-api
- 회원 관리 API

# 1. 프로젝트 설명
### 기술 스택
- Kotlin, JDK 17
- Spring Boot 3.2 x
- Spring Data JPA
- MySQL (Docker)
- Spring Security
- JWT

### 프로젝트 개요
- 사용자 인증 및 관리를 위한 RESTful API 서버입니다.
- Spring Boot, Kotlin 기반으로 JWT 인증, 사용자 정보 관리, 예외 처리 등을 제공합니다.

### 주요 기능
- 회원가입, 로그인, 사용자 정보 조회 및 수정, 전체 목록 조회
- JWT 기반 인증 및 보안 처리
- Spring Security를 활용한 인증/인가
- 커스텀 예외 및 일관된 에러 응답


# 2. 프로젝트 실행
### DB 환경 셋업
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

### 실행 방법
1. application.yml DB 정보 확인 및 수정
   - src/main/resources/application.yml

2. 프로젝트 실행
```bash
./gradlew bootRun
```

3. API 테스트
- Admin 회원가입 후, DB에서 user 테이블의 role 컬럼 'ADMIN'으로 update


4. Test Case 실행


# 기타
### Docker 명령어

- 상태 확인
```bash
docker-compose ps
```
- 중지
```bash
docker-compose down
```

---
### JWT
https://jwt.io/