## 프로젝트 설명
내 게시물을 포함한 모든 게시물을 볼 수 있는 백엔드 서버

## 개발환경
- Language - Java 17
- Spring Boot - 3.2.x version
- IDE - IntelliJ
- DataBase - MySQL
- Project - Gradle(groovy)
- 협업툴 - Github, Notion, Slack, Figma, ERDCloud

## API 명세서
[API명세서 링크](https://www.notion.so/029df8f3e21a44cea5042fffdfece64e)
![image](https://github.com/dev-pokemon/newsfeed/assets/155534061/49b38e3d-d26c-4842-b52a-101b84727223)


## ERD TABLE
![image](https://github.com/dev-pokemon/newsfeed/assets/155534061/c1546a61-fdf0-41fd-91aa-7355a8e848e0)

## WireFrame
![image](https://github.com/dev-pokemon/newsfeed/assets/155534061/007a1a20-fe3c-47a3-825b-23ecd0c354e5)


### 기능 구현사항
- [ ] 사용자 인증 기능
  - [ ] 회원가입 기능
    - [ ]  ID, 비밀번호, E-mail, 이름 형태로 가입
      - [ ] 비밀번호는 암호화되고, 안전하게 저장
  - [ ] 로그인 및 로그아웃 기능
    - [ ] 사용자는 자신의 계정으로 서비스에 로그인/아웃
    
  - [ ] 프로필 관리
    - [ ] 프로필 수정 기능
      - [ ] 이름, 한 줄 소개와 같은 정보 조회/수정
      - [ ] 자신이 등록한 게시물 전체 조회 기능
        - [ ] 비밀번호 수정 시 비밀번호를 한 번 더 입력받기
           
  - [ ] 게시물 CRUD기능
    - [ ] 게시물 작성, 조회, 수정, 삭제 기능
      - [ ] 게시물 작성, 수정, 삭제 기능에 모두 Authorization 적용 (JWT와 같은 토큰 검증 시스템)
    - [ ] 게시물 작성, 수정, 삭제 시 새로고침 기능

  - [ ] 뉴스 피드 기능
    - [ ] 사용자가 다른 사용자의 게시물을 한 눈에 볼 수 있는 전체 조회 기능
        
  
      
