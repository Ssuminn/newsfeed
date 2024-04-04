package com.pokemon.newsfeed;

public class memo {
    // todo: 메모장으로 쓰려고 만들었습니다. 신경쓰지 않으셔도 됩니다!
    /*
    todo: 로그인 기능 구현을 위한 UserDetails 구현을 위해서는 권한값 필요 Role Enum 생성 Entity 수정 Role은 USER로 고정
    todo: UserDetailsImpl은 회원 정보에 대한 내용의 클래스라면
    todo: UserDetailsServiceImpl은 사용자 정보 하나를 받아서 데이터 베이스에서 조회 후 정보가 존재하면 UserDetailsImpl로 변환하여 반환
    todo: 필요없는 import 삭제하지 않고 git에 올려서 conflict 발생 필요없는 import 확인 후 push

    todo: 로그인 기능 구현 및 토큰 발급 후 로그인 정보 반환 받기 확인
    todo: 로그인 시 비밀번호 다를 경우 로그인 불가 처리

    todo: log와 sout 차이
    todo: config와 util 패키지의
    일반 equals와 matches / BCrypt의 equals와 matches랑 다른가?

    todo: BCrypt 암호화하면 입력값을 어떻게 인식하고 처리되는건지 ----- 이거
    todo: timestamp temporal 없이 사용하는 방법 createdAt modifiedAt
    todo: 비밀번호 비교 equals랑 matches 뭐가 더 좋지? ----------- 이거
    todo: Exception처리는....? ------------------------------------- 이거
    todo: 게시글 조회할 때 해당 게시글에 맞는 해당 댓글이 같이 조회가 되게 로직을 만들어야 하는지 ----- 이거

    todo: 회원탈퇴 Soft Delete createdAt ModifiedAt 처럼 DeletedAt을 만들어서 null이면 살아있는 유저 시간이 들어가면 삭제된 것처럼
    todo: DB 설계시 관계 설정 문제 -> 한명의 회원 여러개 게시글 = 1 : 다 / 게시글은 하나 여러개 댓글 = 1 : 다 / 한명의 회원 여러개 댓글 1 : 다 많은 부분이 주인이다.
    todo: Many to One만 공부
    todo: 설계상 좋지 않다 문제가 생길 수 있다. 엔티티를 바로 컨트롤러로 보내면 순환참조 문제가 발생할 수 있다. 문제 방지 계층 분리 문제
    todo: temporal 없이 할 수 있음
    todo: config는 설정만 있음
    todo: util은 실제로 클래스에서 불러다 쓸 수 있음
    todo: userDetailsImpl은 UserDetails를 상속 받아서 구현체를 가져다 쓰기 위함
    todo: userDetailsServiceImpl은 UserDetailsService를 상속 받아서 구현체를 가져다 쓰기 위함

    todo: 취업문제 : 취업은 운이다.
     생각을 넓게하는 자세를 갖는게 중요 ->
     코드 짜는건 여러분들 보다도 저 보다도 쥐피티가 더 잘함 훨씬 잘함 속도도 더 빠름 뚝딱뚝딱 만듬 코드를 자는건 더 이상 중요하지 않다
     여러분들이 엔지니어로서의 생각을 갖추는게 중요하다
     결국 회사에서도 CRUD 하는거다 그 정도만 할줄 알면 됨
     쥐피티가 다 해줌 단지 그 쥐피티가 짜준 코드를 내가 가져다 쓸줄 알아야 됨
     깃헙이란 무기고에 내 무기를 쌓고 있다
     위와 같은 사고를 갖는게 중요 여러분들은 개발에 미쳐 있어야 한다.
     유튜브를 많이 봐라 창의적이게 되려면 배경지식이 있어야 한다.
     그 때문에 유튜브를 많이 봐라 과정을 따라가는 것도 중요하지만
     알렉의 기술노트
     널널한 개발자
     최범균
     우아한 테크
     당근마켓 기술
     카카오 테크
     토스 테크
     2배속으로
     대용량 트래픽 처리 하는 방법은 GPT가 제대로 처리해주지 않기 때문에 -> 유튜브
     답은 유튜브
     쿨라우드 ( = 남의 콤퓨타 빌려 쓰는 개념)


    equals와 matches 중에 equals 사용
    입력값 무조건 암호화해서 비교
    GlobalExceptionHandler 사용 controllerAdvice 찾아보기

    댓글을 리프레쉬하는 기능이 있는 경우 게시글과 댓글을 따로 보는 경우도 있다.

     */
}
