# community-project
Community project with Java and Springframework

이 프로젝트는 커뮤니티 게시판을 구현한 API입니다.
게시물 검색, 게시글 관리, 로그인/로그아웃에 따른 편집 허가 기능이 있습니다.

## 기능 목록
1. 게시물 검색 기능
- 키워드로 게시물 검색
- 검색 결과를 게시글 목록으로 보여줌
2. 게시글 관리
- 게시글 작성
- 게시글 목록 조회
- 작성된 게시글 편집 기능
3. 로그인 / 로그 아웃에 따른 편집 허가 기능 구현
- 로그인 후에는 게시글 작성 및 수정 가능
- 로그아웃 후에는 게시글 작성 및 수정 불가능


## ERD(Entity-Relationship Diagram)

이 프로젝트에서는 유저(User) 테이블과 게시글(Post) 테이블 2개로 구성하였습니다.
각각의 속성과 관계는 아래와 같습니다.

#### [User Table]
- id: int, PK (유저 고유 번호)
- username: varchar(50) (유저 아이디)
- password: varchar(100) (유저 비밀번호)
- created_at: datetime (계정 생성 일시)

#### [Post Table]
- id: int, PK (게시글 고유 번호)
- title: varchar(100) (게시글 제목)
- content: text (게시글 내용)
- user_id: int, FK (게시글 작성자 고유 번호)
- created_at: datetime (게시글 작성 일시)


## 기술 스택
커뮤니티 프로젝트의 백엔드 구현을 위해 아래와 같은 기술 스택과 라이브러리가 사용될 수 있습니다.

- 언어: Java 11 - eclipse
- 프레임워크: Spring Boot 2.7.11
- ORM: Spring Data JPA
- 데이터베이스: MySQL 8.0
- 빌드 및 의존성 관리: Gradle
- 인증 및 보안: Spring Security, JWT
- 로깅: SLF4J
- 테스트: JUnit5

위 기술 스택과 라이브러리를 활용하여 각 기능에 맞는 REST API를 개발하고, 데이터베이스와 연동하여 데이터를 저장 및 조회할 수 있도록 구현합니다. Spring Security 또는 JWT를 사용해 로그인/로그아웃 권한 처리 구현하며 로깅은 SLF4J, Logback을 사용하고 테스트는 JUnit으로 수행할 예정입니다.
