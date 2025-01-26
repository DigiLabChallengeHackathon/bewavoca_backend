
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

### 1. 기기 존재 여부 및 사용자 진행 상황 확인
**URL**: `/api/auth/check-device`  
**Method**: `POST`  
**Request Body**:
```json
{
  "deviceId": "string",
  "userid": "string",
  "nickname": "string",
  "character": 1,
  "region": 1,
  "level": 1
}
```
**Response**:
```json
{
  "status": "success",
  "message": "User exists and progress retrieved",
  "data": {
    "userid": 1,
    "nickname": "string",
    "character": 1,
    "region": 1,
    "level": 1,
    "clearedStages": ["1-1", "1-2"],
    "clearedRegions": [1]
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

### 3. 닉네임 업데이트
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

### 4. 사용 가능한 캐릭터 목록 조회
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

### 5. 캐릭터 선택
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

### 6. 선택된 캐릭터 정보 조회
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

### 7. 퀴즈 데이터 조회
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

### 8. 퀴즈 완료 처리
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
