package com.pokemon.newsfeed.controller;

import com.pokemon.newsfeed.dto.requestDto.LoginRequestDto;
import com.pokemon.newsfeed.dto.requestDto.SignupRequestDto;
import com.pokemon.newsfeed.dto.requestDto.UserUpdateDto;
import com.pokemon.newsfeed.dto.responseDto.LoginResponseDto;
import com.pokemon.newsfeed.dto.responseDto.ProfileResponseDto;
import com.pokemon.newsfeed.security.UserDetailsImpl;
import com.pokemon.newsfeed.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup (@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        // TODO: RequestBody 어노테이션 없으면 null이 들어오는 이유// Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "회원가입도중 에러가 발생했습니다.";
        }
        // 회원 가입도중 발생할 에러를 출력합니다.

        userService.signup(requestDto);
        // 회원가입을 위해 입력한 정보를 서비스 클래스의 signup 메서드에 보냅니다.

        return "회원가입 성공";
    }

    @PostMapping("/login")
    public LoginResponseDto login (@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto responseDto = userService.login(requestDto);

        return responseDto;
        /*
        유저 id와 비밀번호를 파라미터로 받아서 서비스의 login 메서드로 넘겨줍니다.
        서비스에서 로직이 성공적으로 구현되면 로그인 하려는 유정의 정보를 볼 수 있는 ResponseDto를 반환 받습니다.
         */
    }

    // 회원 관련 정보 받기 + 강의에서 봤던 이름님 환영합니다 부분 연결 api
    @GetMapping("/info")
    @ResponseBody
    public UserDetailsImpl getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return userDetails;
        // TODO: 이 부분 필요할까....
        // 추후에 필요하지 않을까 생각이 듦
    }

    // 회원탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(userDetails.getUser());
        return ResponseEntity.ok("회원 탈퇴 완료");
    }

    // 프로필 단건조회
    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto response = userService.getProfile(userDetails.getUser().getUserNum());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 프로필 수정
    @PutMapping
    public ResponseEntity<ProfileResponseDto> updateProfile(@RequestBody UserUpdateDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto response = userService.updateProfile(userDetails.getUser().getUserNum(), request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
