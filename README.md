# BO Project - AI 기반 패션 쇼핑몰

> **OpenAI API를 활용한 개인 맞춤형 패션 추천 시스템**

패션에 관심이 많지만 스타일 선택에 어려움을 겪는 사용자들을 위한 AI 기반 패션 쇼핑몰입니다.  
사용자의 취향과 상황에 맞는 패션 아이템을 AI가 분석하고 추천해주며, OOTD 커뮤니티를 통해 사용자들 간의 활발한 소통을 지원합니다.

## 🎥 프로젝트 데모

### 📺 전체 기능 시연 영상
> 🔗 **[전체 시연 영상 보기](https://www.youtube.com/watch?v=fc3utoxBQs8&t=16s)**

### 🖼️ 주요 기능 미리보기

#### 🤖 AI 상품 추천 시스템
![AI 추천 기능](./gif_files/aifeature.gif)
*사용자 입력에 따른 AI 개인 맞춤형 상품 추천*

#### 👗 OOTD 게시판
![OOTD 게시판](./gif_files/article.gif)
*사용자들의 스타일 공유 및 커뮤니티 활동*

#### 상품 및 주문현황 보기

![상품리스트, 구매 후 주문현황 보기]()

## 🎯 프로젝트 배경

1. **패션 집입장벽 낮춘 맞춤형 쇼핑몰**
2. **누구나 쉽게 입을 수 있는 패션 큐레이션**
3. **고민 없는 스타일 선택**
4. **실고 친절한 AI패션 안내자**

## ✨ 주요 기능

### AI 추천 시스템
- **OpenAI API 활용**: 사용자의 입력 데이터를 분석하여 개인 맞춤형 상품 추천
- **AI 코멘트**: 추천 아이템에 대한 상세한 설명과 스타일링 팁 제공
- **개인화된 큐레이션**: 사용자 선호도 학습을 통한 지속적인 추천 품질 향상

### OOTD 커뮤니티
- **스타일 공유**: 사용자들 간의 OOTD(Outfit of The Day) 게시글 공유
- **커뮤니티 활성화**: 좋아요, 댓글 기능을 통한 활발한 소통 지원
- **이미지 저장**: AWS S3를 활용한 안정적인 이미지 저장 시스템

### 쇼핑 기능
- **상품 관리**: 다양한 패션 아이템 카탈로그
- **주문 시스템**: 간편한 주문 및 배송 관리
- **위시 리스트**: 관심 상품 저장 기능

## 🛠️ 기술 스택

### Frontend
![HTML5](https://img.shields.io/badge/HTML5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

### Backend
![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![HttpServer](https://img.shields.io/badge/HttpServer-Custom-blue?style=for-the-badge)

### Database
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-Connection-red?style=for-the-badge)

### Cloud & AI
![AWS S3](https://img.shields.io/badge/AWS%20S3-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![OpenAI](https://img.shields.io/badge/OpenAI%20API-412991?style=for-the-badge&logo=openai&logoColor=white)

### Tools
![Eclipse](https://img.shields.io/badge/Eclipse-2C2255?style=for-the-badge&logo=eclipse&logoColor=white)
![VS Code](https://img.shields.io/badge/VS%20Code-0078d4.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

## 🏗️ 프로젝트 구조

```
BOProject/
├── 📁 Frontend/
│   ├── 📄 BOView/           # HTML 뷰 파일들
│   ├── 📄 BO.Css/          # CSS 스타일시트
│   ├── 📄 BO.Event/        # JavaScript 이벤트 처리
│   └── 📄 BO.Controller/   # JavaScript 컨트롤러
│
├── 📁 Backend/
│   ├── 📄 BO.Service/      # 비즈니스 로직
│   ├── 📄 BO.Dao/          # 데이터 액세스 객체
│   ├── 📄 BO.Model/        # 데이터 모델 (VO)
│   ├── 📄 BO.Util/         # 유틸리티 클래스
│   └── 📁 BO.Server/       # HTTP 서버 관련
│       ├── 📄 BO.Server.Router/
│       ├── 📄 BO.Server.Model/
│       └── 📄 BO.Server.Path/
│           ├── BO.Server.Path.Ai
│           ├── BO.Server.Path.Article
│           ├── BO.Server.Path.Product
│           └── BO.Server.Path.User
│
└── 📁 Database/
    └── 📄 Oracle DB Schema
```

## 🚀 주요 기술적 특징

### 💡 커스텀 HTTP 서버
- **Pure Java HttpServer 사용**: Servlet이나 Spring 없이 순수 Java HTTP 서버로 구현
- **학습 목적**: 웹 서버의 동작 원리 이해를 위한 저수준 구현
- **Router 패턴**: RESTful API 구조를 위한 커스텀 라우터 구현

### 🎨 Frontend-Backend 통신
- **Axios 기반**: JavaScript에서 Java 서버로 비동기 HTTP 요청
- **JSON 데이터 교환**: 효율적인 데이터 통신을 위한 JSON 형식 사용
- **동적 HTML 업데이트**: 서버 응답에 따른 실시간 UI 변경

### 🖼️ 이미지 처리 시스템
- **AWS S3 연동**: 사용자 업로드 이미지를 S3에 안전하게 저장
- **URL 기반 관리**: S3 URL을 통한 효율적인 이미지 관리
- **최적화**: 이미지 로딩 성능 최적화

### 🗄️ 데이터베이스 아키텍처
- **JDBC 직접 연결**: Oracle DB와의 순수 JDBC 연결
- **계층 분리**: DAO, Service, Controller 패턴을 통한 관심사 분리
- **트랜잭션 관리**: 데이터 일관성을 위한 트랜잭션 처리

## 📈 기대 효과
- **사용자 만족도 향상**: AI 기반 개인화 추천으로 쇼핑 경험 개선
- **커뮤니티 활성화**: OOTD 게시판을 통한 사용자 참여도 증가
- **매출 증대**: 개인 맞춤형 추천과 커뮤니티 활동을 통한 구매 전환율 향상
- **브랜드 차별화**: AI 기술을 활용한 독특한 쇼핑 경험 제공

### 🎯 핵심 구현 사항
1. **OpenAI API 연동**: 사용자 취향 분석 및 상품 추천 알고리즘 구현
2. **커스텀 웹 서버**: HTTP 서버 직접 구현을 통한 웹 서버 동작 원리 학습
3. **이미지 업로드 시스템**: AWS S3와 연동한 안정적인 파일 저장 시스템
4. **반응형 UI**: 사용자 친화적인 인터페이스 구현

### 🛡️ 해결한 기술적 도전
- Java HttpServer의 멀티스레딩 처리
- CORS 정책 해결을 위한 헤더 설정
- 대용량 이미지 업로드 최적화
- Oracle DB 커넥션 풀링 구현

## 🧭 클라이언트 사이드 렌더링 아키텍처 (CSR)

브라우저(클라이언트)가 **Axios**로 Java 서버에 **데이터만 요청**하고,  
서버는 **Oracle**에서 데이터를 조회해 **JSON**으로 응답합니다.  
HTML 렌더링은 **브라우저의 JavaScript가 DOM을 갱신**하는 방식으로 수행됩니다.

### 요청 흐름

```mermaid
sequenceDiagram
    autonumber
    participant U as User (Browser)
    participant F as Frontend (HTML/CSS/JS)
    participant A as Axios
    participant S as Java HttpServer
    participant D as Oracle (JDBC)

    U->>F: 페이지 로드
    F->>A: Axios GET /api/products?category=...
    A->>S: HTTP 요청 (JSON 기대)
    S->>D: JDBC 쿼리 실행
    D-->>S: ResultSet 반환
    S-->>A: 200 OK + JSON
    A-->>F: 데이터 수신
    F->>F: JS로 DOM 업데이트(템플릿 렌더)


---

**💡 "AI와 커뮤니티가 만나는 새로운 패션 쇼핑 경험"**
