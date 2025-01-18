
# Backend API Documentation

## 프로젝트 소개
이 프로젝트는 **베와보카** 애플리케이션의 백엔드입니다. 주요 기능은 다음과 같습니다:
- 사용자 인증 및 회원 관리
- 캐릭터 데이터 관리
- 퀴즈 생성 및 결과 처리

## 기술 스택
- Java
- Spring Boot
- MySQL
- 기타 라이브러리 (Lombok, JPA 등)

## API 명세

### 1. 기기 존재 여부 확인
**URL**: `/api/auth/check-device`  
**Method**: `POST`  
**Request Body**:
```json
{
  "deviceId": "string"
}
```
**Response**:
```json
{
  "status": "success",
  "message": "User exists",
  "data": {
    "userId": 1,
    "nickname": "string"
  }
}
```
---

### 2. 회원가입
**URL**: `/api/auth/signup`  
**Method**: `POST`  
**Request Body**:
```json
{
  "deviceId": "string",
  "nickname": "string"
}
```
**Response**:
```json
{
  "status": "success",
  "message": "User registered successfully",
  "data": {
    "userId": 1,
    "nickname": "string"
  }
}
```
---

### 3. 사용자 진행 상황 조회
**URL**: `/api/auth/progress/{deviceId}`  
**Method**: `GET`  
**Response**:
```json
{
  "status": "success",
  "message": "Progress retrieved successfully",
  "data": {
    "clearedStages": ["string()"],
    "clearedRegions": [1, 2]
  }
}
```
---

### 4. 닉네임 업데이트
**URL**: `/api/auth/nickname`  
**Method**: `PATCH`  
**Request Body**:
```json
{
  "deviceId": "string",
  "newNickname": "string"
}
```
**Response**:
```json
{
  "status": "success",
  "message": "Nickname updated successfully",
  "data": {
    "userId": 1,
    "nickname": "string"
  }
}
```
---

### 5. 사용 가능한 캐릭터 목록 조회
**URL**: `/api/character/{deviceId}`  
**Method**: `GET`  
**Response**:
```json
{
  "status": "success",
  "message": "Characters retrieved successfully.",
  "data": [
    {
      "id": 1,
      "name": "string",
      "description": "string",
      "dialogue": "string",
      "regionName": "string"
    }
  ]
}
```
---

### 6. 캐릭터 선택
**URL**: `/api/character/select`  
**Method**: `POST`  
**Request Body**:
```json
{
  "deviceId": "string",
  "characterId": 1
}
```
**Response**:
```json
{
  "status": "success",
  "message": "Character selected successfully",
  "data": null
}
```
---

### 7. 선택된 캐릭터 정보 조회
**URL**: `/api/character/selected/{deviceId}`  
**Method**: `GET`  
**Response**:
```json
{
  "status": "success",
  "message": "Selected character retrieved",
  "data": {
    "id": 1,
    "name": "string",
    "description": "string",
    "dialogue": "string",
    "regionName": "string"
  }
}
```
---

### 8. 퀴즈 데이터 조회
**URL**: `/api/quiz`  
**Method**: `GET`  
**Query Parameters**:
- `level` (required): int  
- `type` (required): string (e.g., ox, match, choice)

**Response**:
```json
{
  "status": "success",
  "message": "Quiz retrieved successfully",
  "data": {
    "type": "string",
    "level": 1,
    "quizzes": [{}]
  }
}
```
---

### 9. 퀴즈 완료 처리
**URL**: `/api/quiz/complete`  
**Method**: `POST`  
**Request Body**:
```json
{
  "deviceId": "string",
  "region": 1,
  "stage": 1,
  "resultStatus": "GREAT_SUCCESS"
}
```
**Response**:
```json
{
  "status": "success",
  "message": "Quiz completed successfully with great success! Stage cleared.",
  "data": null
}
```

## 실행 방법
1. **환경 설정**:
   - `.env` 파일을 생성하고 데이터베이스 정보 및 기타 환경 변수를 설정하세요.
2. **빌드 및 실행**:
   ```bash
   ./gradlew bootRun
   ```

## 기여 방법
1. 이슈를 생성하거나 Pull Request를 제출하세요.
2. 명확한 커밋 메시지와 상세한 설명을 남겨 주세요.
