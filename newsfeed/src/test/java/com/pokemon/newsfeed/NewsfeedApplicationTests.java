//package com.pokemon.newsfeed;
//
//import com.pokemon.newsfeed.dto.requestDto.SignupRequestDto;
//import com.pokemon.newsfeed.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//@SpringBootTest // Spring Boot 어플리케이션 컨텍스트를 로드하여 테스트
//@ActiveProfiles("dev") // test 프로파일을 사용하여 application-dev.properties를 사용
//class NewsfeedApplicationTests {
//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    public void testSignup() {
//        for (int i = 1; i <= 10; i++) {
//            SignupRequestDto requestDto = new SignupRequestDto();
//            requestDto.setUserId("test"+i);
//            requestDto.setPassword(i+"");
//            requestDto.setEmail("test"+i+"@"+"test"+i+".com");
//            requestDto.setName("nameTest"+i);
//
//            userService.signup(requestDto);
//        }
//    }
//}
