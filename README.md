# Bewavoca(베와보카)

베와보카(Bewavoca)는 DigiLab Hackathon에서 개발된 제주어 학습 앱입니다.
Bewavoca is a Jeju language learning app developed during the DigiLab Hackathon.

🏆 DigiLab 해커톤 최우수상 수상 | 제주지역혁신플랫폼 지원 선정
🏆 Excellence Award Winner at DigiLab Hackathon | Supported by Jeju Regional Innovation Platform

사용자들이 재미있는 게임 형식을 통해 제주어를 쉽고 즐겁게 학습할 수 있도록 설계되었습니다.
The app is designed to help users learn Jeju dialect easily and enjoyably through engaging game formats.

이 앱은 퀴즈 기반의 학습 시스템을 통해 제주어에 대한 이해와 흥미를 높이는 것을 목표로 합니다.
This app aims to enhance understanding and interest in the Jeju language through quiz-based learning systems.

---

## 📖 목차 | Table of Contents

- [프로젝트 소개](#프로젝트-소개) | Introduction
- [주요 기능](#주요-기능) | Features
- [기술 스택](#기술-스택) | Tech Stack
- [라이센스](#License) | License
- [Contact](#Contact) | Contact

---

## 프로젝트 소개 | Introduction

### 🔍 문제 | Problem
제주어는 유네스코에서 사라져가는 언어로 지정되었으며, 이를 학습할 수 있는 기회는 매우 제한적입니다. 특히, 현대적인 방식으로 언어를 배우는 도구가 부족합니다.
Jeju language is designated by UNESCO as a critically endangered language, and learning opportunities are very limited.

### 💡 해결책 | Solution
모사불겨는 OX 퀴즈, 매칭 게임, 4지선다형 퀴즈를 통해 제주어를 배우는 앱입니다. 사용자는 다양한 레벨과 지역에 따라 언어를 학습하고 점차적으로 제주어에 익숙해질 수 있습니다.
In particular, there is a lack of modern tools to facilitate language learning.

---

## 주요 기능 | Features

### 1. 🌐 사용자 인증 및 회원가입 | User Authentication and Registration

**기능 설명** | Description
- 사용자는 기기 ID를 기반으로 기존 사용자 여부를 확인합니다. | Users can check if they are existing users based on their device ID.
- 신규 사용자인 경우 닉네임을 입력하여 회원가입을 진행합니다. | New users can register by entering a nickname.
- 회원가입 시 기본 캐릭터가 자동으로 설정됩니다. | A default character is automatically assigned upon registration.
- iOS에서 `UIDevice.identifierForVendor`를 사용하여 고유 기기 ID를 생성합니다. | The UIDevice.identifierForVendor is used in iOS to generate a unique device ID.

**백엔드 구현 (Spring Boot)**  
- **API 설계**: | **API Design**
  - `POST /api/auth/check-device`: 사용자 존재 여부 확인. | Checks user existence.
  - `POST /api/auth/signup`: 닉네임과 기기 ID를 기반으로 사용자 등록. | Registers a user using a nickname and device ID.
- **서비스 계층**: | **Service Layer**
  - 사용자 등록 시 기본 캐릭터(ID: 1) 자동 설정. | Automatically assigns a default character (ID: 1) during registration.
  - 데이터베이스와 연동하여 사용자를 생성하고 상태 관리. | Integrates with the database to create and manage user states.
 
---

### 2. 📚 사용자 관리 | User Management

**기능 설명**  
- 사용자가 기기 ID를 제공하면, 잠금 해제된 캐릭터 목록을 반환합니다. | Users provide a device ID to retrieve the list of unlocked characters.
- 사용자는 캐릭터를 선택하여 정보를 확인하거나 맵 화면으로 돌아갈 수 있습니다. | Users can select a character to view information or return to the map screen.

**백엔드 구현 (Spring Boot)**  
- **API 설계**:
  - `GET /api/character/{deviceId}`: 사용 가능한 캐릭터 목록 조회. | Retrieves the list of available characters.
  - `POST /api/character/select`: 캐릭터 선택 저장. | Saves the selected character.
  - `GET /api/character/selected/{deviceId}`: 선택된 캐릭터 정보 조회. | Retrieves information about the selected character.
- **기술적 구현**:
  - 캐릭터의 표정별 외형 데이터를 맵(Map) 형태로 관리. | Manages character appearance data for different expressions using a map structure.
  - 사용자의 완료된 지역 정보를 바탕으로 잠금 해제된 캐릭터 목록 필터링. | Filters the unlocked character list based on the user’s completed regions.
  - 데이터베이스에서 캐릭터와 지역 간 관계를 `@ManyToOne`으로 설정. | Establishes a @ManyToOne relationship between characters and regions in the database.
 
---

### 3. 🎮 퀴즈 | Quizzes

**기능 설명** | Description
- 사용자는 지역 레벨과 퀴즈 유형(OX, 매칭, 객관식)에 따라 퀴즈를 풉니다. | Users take quizzes based on regional levels and quiz types (OX, matching, multiple-choice).
- 퀴즈 완료 여부에 따라 스테이지와 지역의 완료 상태가 업데이트됩니다. | Quiz completion updates the stage and region status.

**백엔드 구현 (Spring Boot)**  
- **API 설계**:
  - `GET /api/quiz`: 퀴즈 데이터 조회. | Retrieves quiz data.
  - `POST /api/quiz/complete`: 퀴즈 완료 처리. | Processes quiz completion.
- **기술적 구현**:
  - `QuizResponseMapper`를 통해 퀴즈 데이터를 DTO로 매핑: | Maps quiz data to DTOs using QuizResponseMapper:
    - OX, 매칭, 객관식 퀴즈 유형별 데이터 변환. | Transforms data based on OX, matching, and multiple-choice quiz types.
    - 유형별 DTO 서브클래스를 사용해 클라이언트와 데이터 구조 일관성 유지. | Uses type-specific DTO subclasses to maintain consistent data structures with the client.
  - 퀴즈 완료 요청 시, 사용자의 진행 상태를 업데이트: | Updates the user’s progress status upon quiz completion:
    - 스테이지 완료 여부(`clearedStageTypes`) 및 지역 완료 여부(`completedRegions`)를 `User` 엔티티에서 관리. | Manages stage completion (clearedStageTypes) and region completion (completedRegions) in the User entity.

---

### 🎮 게임 모드 | Game Modes
1. **OX 퀴즈** | OX Quizzes
   - OX 형식으로 간단한 질문을 통해 제주어를 학습합니다. | Learn Jeju dialect through simple OX-style questions.
2. **매칭 게임** | Matching Game
   - 표준어와 제주어를 매칭하며 학습합니다. | Match Standard Korean with Jeju words to learn effectively.
3. **4지선다형 퀴즈** | Multiple Choice Quiz
   - 제주어 정답을 선택하는 방식으로 학습을 진행합니다. | Choose the correct Jeju word from multiple choices to learn.

### 📚 학습 단계 | Learning Stages
- **레벨 시스템**: 지역별로 학습 단계를 나누어 난이도에 따라 학습 가능.
- **Level System**: Divides learning stages by region, allowing users to progress based on difficulty.
- **캐릭터 시스템**: 학습을 진행하며 캐릭터를 잠금 해제.
- **Character System**: Unlocks new characters as users advance in their learning journey.

### 🌐 API 기반 백엔드 | API-Based Backend
- RESTful API를 통해 iOS 클라이언트와 통신하며 퀴즈 데이터를 제공합니다.
- Communicates with the iOS client via RESTful APIs to provide quiz data.

### Challenges
- 서버가 다운될 떄마다 AWS 인스턴스를 재부팅하는 과정에서 새로운 ip주소를 받게 되어 설정 시간이 많이 소모되었는데 Elastic IP 도입으로 해결하였습니다.
  
---

## License

이 프로젝트는 MIT 라이센스를 따릅니다. 자유롭게 사용하세요!
This project is licensed under the MIT License.

자세한 내용은 LICENSE 파일을 참고하세요.
For more details, see the LICENSE file.

---

## Contact

프로젝트 관련 문의는 아래 이메일로 연락 부탁드립니다:
For inquiries about the project, please contact:

📧 andrewkimswe@gmail.com
