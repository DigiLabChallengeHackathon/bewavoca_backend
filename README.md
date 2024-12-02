# Bewavoca(베와보카)

베와보카(Bewavoca)는 DigiLab Hackathon에서 개발된 제주어 학습 앱입니다.
Bewavoca is a Jeju language learning app developed during the DigiLab Hackathon.

🏆 DigiLab 해커톤 최우수상 수상 | 제주지역혁신플랫폼 지원 선정
🏆 Grand Prize Winner at DigiLab Hackathon | Supported by Jeju Regional Innovation Platform

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

**기능 설명**  
- 사용자는 기기 ID를 기반으로 기존 사용자 여부를 확인합니다.  
- 신규 사용자인 경우 닉네임을 입력하여 회원가입을 진행합니다.  
- 회원가입 시 기본 캐릭터가 자동으로 설정됩니다.  
- iOS에서 `UIDevice.identifierForVendor`를 사용하여 고유 기기 ID를 생성합니다.

**iOS 구현 (SwiftUI)**  
- **SplashView** → **CheckDeviceView**로 전환.  
- **AuthService**를 활용해 API 호출:
  - `checkDevice`: 기존 사용자 확인.
  - `signup`: 신규 사용자 등록.  

**백엔드 구현 (Spring Boot)**  
- **API 설계**:
  - `POST /api/auth/check-device`: 사용자 존재 여부 확인.
  - `POST /api/auth/signup`: 닉네임과 기기 ID를 기반으로 사용자 등록.
- **서비스 계층**:
  - 사용자 등록 시 기본 캐릭터(ID: 1) 자동 설정.
  - 데이터베이스와 연동하여 사용자를 생성하고 상태 관리.
 
---

### 2. 📚 사용자 관리

**기능 설명**  
- 사용자가 기기 ID를 제공하면, 잠금 해제된 캐릭터 목록을 반환합니다.  
- 사용자는 캐릭터를 선택하여 정보를 확인하거나 맵 화면으로 돌아갈 수 있습니다.

**iOS 구현 (SwiftUI)**  
- 캐릭터 선택 화면에 진입 시 서버로부터 잠금 해제된 캐릭터 목록을 가져옵니다.  
- 선택된 캐릭터의 이름과 대사를 화면에 표시합니다.  
- 우측 상단의 "X" 버튼을 눌러 맵 화면으로 이동 가능합니다.

**백엔드 구현 (Spring Boot)**  
- **API 설계**:
  - `GET /api/character/{deviceId}`: 사용 가능한 캐릭터 목록 조회.
  - `POST /api/character/select`: 캐릭터 선택 저장.
  - `GET /api/character/selected/{deviceId}`: 선택된 캐릭터 정보 조회.
- **기술적 구현**:
  - 캐릭터의 표정별 외형 데이터를 맵(Map) 형태로 관리.
  - 사용자의 완료된 지역 정보를 바탕으로 잠금 해제된 캐릭터 목록 필터링.
  - 데이터베이스에서 캐릭터와 지역 간 관계를 `@ManyToOne`으로 설정.
 
---

### 3. 🎮 퀴즈

**기능 설명**  
- 사용자는 지역 레벨과 퀴즈 유형(OX, 매칭, 객관식)에 따라 퀴즈를 풉니다.  
- 퀴즈 완료 여부에 따라 스테이지와 지역의 완료 상태가 업데이트됩니다.

**백엔드 구현 (Spring Boot)**  
- **API 설계**:
  - `GET /api/quiz`: 퀴즈 데이터 조회.
  - `POST /api/quiz/complete`: 퀴즈 완료 처리.
- **기술적 구현**:
  - `QuizResponseMapper`를 통해 퀴즈 데이터를 DTO로 매핑:
    - OX, 매칭, 객관식 퀴즈 유형별 데이터 변환.
    - 유형별 DTO 서브클래스를 사용해 클라이언트와 데이터 구조 일관성 유지.
  - 퀴즈 완료 요청 시, 사용자의 진행 상태를 업데이트:
    - 스테이지 완료 여부(`clearedStageTypes`) 및 지역 완료 여부(`completedRegions`)를 `User` 엔티티에서 관리.

---

### 🎮 게임 모드
1. **OX 퀴즈**
   - OX 형식으로 간단한 질문을 통해 제주어를 학습합니다.
2. **매칭 게임**
   - 표준어와 제주어를 매칭하며 학습합니다.
3. **4지선다형 퀴즈**
   - 제주어 정답을 선택하는 방식으로 학습을 진행합니다.

### 📚 학습 단계
- **레벨 시스템**: 지역별로 학습 단계를 나누어 난이도에 따라 학습 가능.
- **캐릭터 시스템**: 학습을 진행하며 캐릭터를 잠금 해제.

### 🌐 API 기반 백엔드
- RESTful API를 통해 iOS 클라이언트와 통신하며 퀴즈 데이터를 제공합니다.

---

## 기술 스택

### 📱 iOS
- **SwiftUI**:  
  iOS에서 사용자 인터페이스(UI)를 구축하는 현대적인 프레임워크입니다. 선언형 구문을 사용하여 빠르고 직관적으로 UI를 설계할 수 있으며, 상태 기반 설계(State-driven Design)를 통해 데이터 변경에 따라 UI가 자동으로 업데이트됩니다.  

  **효과**:
  - 코드 작성량 감소로 빠른 UI 개발 가능.
  - 상태 관리와 뷰 계층의 간결화로 유지보수 용이.
  - 애니메이션과 UI 전환의 자연스러운 구현.

- **Alamofire**:  
  Swift에서 HTTP 네트워킹을 쉽게 처리할 수 있는 라이브러리입니다. RESTful API와의 통신, JSON 데이터 디코딩, 인증 토큰 처리 등을 단순화합니다.  

  **효과**:
  - 비동기 네트워크 요청을 간단히 구현.
  - API 호출 결과를 간단하게 처리할 수 있어 네트워크 로직 작성 시간 단축.
  - 커스텀 헤더 및 파라미터 설정 등 유연한 요청 처리.

- **MVVM 패턴**:  
  MVVM(Model-View-ViewModel) 아키텍처 패턴을 기반으로 설계되었으며, 이를 통해 코드의 유지보수성, 재사용성, 그리고 테스트 용이성을 극대화했습니다  

  **효과**:
  - View와 ViewModel 간의 의존성을 느슨하게 유지.
  - API 호출 결과를 간단하게 처리할 수 있어 네트워크 로직 작성 시간 단축.
  - 커스텀 헤더 및 파라미터 설정 등 유연한 요청 처리.
 
- **서비스 인스턴스화**:  
  **단일 책임 원칙(Single Responsibility Principle)**을 준수하는 설계방식을 사용했습니다.  

  **효과**:
  - 코드 중복을 줄이고, 사운드 변경이나 추가 시 관리 용이.
  - 로직을 변경할 때 중앙에서만 수정하면 되어 관리가 편리.
  - 단일 지점에서 처리하므로, 오류 처리 및 로깅이 일관되게 적용.
    
---

### 💻 백엔드
- **Spring Boot**:  
  자바 기반의 백엔드 개발 프레임워크로, 빠른 설정과 개발을 지원합니다. RESTful API 개발에 최적화되어 있으며, 스프링 생태계의 다른 모듈과도 긴밀히 통합됩니다.  

  **효과**:
  - 내장 서버(Tomcat)를 통해 애플리케이션 실행이 간편.
  - 의존성 관리와 설정이 자동화되어 개발 효율성 향상.
  - RESTful API 설계 및 데이터베이스 연동이 용이.

- **MySQL**:  
  오픈 소스 관계형 데이터베이스로, 대규모 데이터 처리를 지원하며 안정성과 성능이 뛰어납니다.  

  **효과**:
  - 데이터의 일관성과 무결성 보장.
  - 다양한 쿼리 최적화로 빠른 데이터 검색 가능.
  - 복잡한 데이터 관계를 효율적으로 관리.

- **JPA (Java Persistence API)**:  
  객체와 관계형 데이터베이스 간의 매핑을 지원하는 ORM(Object Relational Mapping) 기술입니다. 엔티티 객체를 사용해 데이터베이스를 제어하며, SQL 작성의 복잡성을 줄여줍니다.  

  **효과**:
  - 데이터베이스 중심이 아닌 객체 중심의 개발 가능.
  - 쿼리 자동 생성으로 개발 생산성 증가.
  - 유지보수성과 확장성이 높은 데이터베이스 연동.

---

### 🛠 기타
- **AWS EC2**:  
  클라우드 기반의 가상 서버로, 애플리케이션 호스팅 및 배포를 제공합니다.
  A cloud-based virtual server for hosting and deploying applications.

  **효과**:
  - 서버 리소스를 탄력적으로 조정 가능.
  - 다양한 운영 체제를 지원하여 유연한 환경 설정 가능.
  - 글로벌 네트워크를 통해 빠른 서비스 제공.
  **Benefits**:
	- Allows flexible scaling of server resources.
	-	Supports various operating systems for versatile environment configuration.
	-	Provides fast service delivery through a global network.

- **NGINX**:
  고성능 HTTP 서버이자 Reverse Proxy로 사용됩니다. 정적 파일 제공과 동시에 애플리케이션 서버로의 요청을 분배합니다.
  A high-performance HTTP server and reverse proxy that serves static files and distributes requests to the application server.

  **효과**:
  - 트래픽 부하를 효율적으로 분산하여 서버 성능 최적화.
  - HTTPS 설정을 통해 보안 강화.
  - 캐싱 기능으로 정적 콘텐츠 전송 속도 개선.
  **Benefits**:
	•	Efficiently distributes traffic loads, optimizing server performance.
	•	Enhances security through HTTPS configuration.
	•	Improves delivery speed of static content with caching functionality.

- **GitHub Actions**:
  자동화된 CI/CD 파이프라인을 지원하여 애플리케이션 빌드, 테스트, 배포를 간소화합니다.
  Supports automated CI/CD pipelines to streamline application building, testing, and deployment.

  **효과**:
  - 코드 푸시 시 자동으로 테스트 및 빌드 실행.
  - AWS EC2에 애플리케이션 배포 자동화.
  - 지속적인 통합(CI)과 배포(CD)로 개발 속도 및 안정성 향상.
  **Benefits**:
	•	Automatically runs tests and builds when code is pushed.
	•	Automates deployment of applications to AWS EC2.
	•	Improves development speed and stability with continuous integration (CI) and delivery (CD).

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
