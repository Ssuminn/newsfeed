package com.pokemon.newsfeed;

import com.pokemon.newsfeed.dto.requestDto.LoginRequestDto;
import com.pokemon.newsfeed.dto.requestDto.SignupRequestDto;
import com.pokemon.newsfeed.dto.responseDto.LoginResponseDto;
import com.pokemon.newsfeed.entity.User;
import com.pokemon.newsfeed.entity.UserRoleEnum;
import com.pokemon.newsfeed.repository.UserRepository;
import com.pokemon.newsfeed.service.UserService;
import com.pokemon.newsfeed.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup_Success() {
        SignupRequestDto requestDto = new SignupRequestDto();
        User existingUser = new User("testuser", "encodedpassword", "test@example.com", "Test User", UserRoleEnum.USER);

        when(userRepository.findByUserId(requestDto.getUserId())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        assertDoesNotThrow(() -> userService.signup(requestDto));
    }

    @Test
    void testSignup_DuplicateUserId() {
        SignupRequestDto requestDto = new SignupRequestDto();
        User existingUser = new User("testuser", "encodedpassword", "test@example.com", "Test User", UserRoleEnum.USER);

        when(userRepository.findByUserId(requestDto.getUserId())).thenReturn(Optional.of(existingUser));

        assertThrows(IllegalArgumentException.class, () -> userService.signup(requestDto));
    }

    // Add more test cases for signup method...

    @Test
    void testLogin_Success() {
        LoginRequestDto requestDto = new LoginRequestDto();
        User existingUser = new User("testuser", "encodedpassword", "test@example.com", "Test User", UserRoleEnum.USER);

        when(userRepository.findByUserId(requestDto.getUserId())).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(requestDto.getPassword(), existingUser.getPassword())).thenReturn(true);
        when(jwtUtil.createToken(existingUser.getUserId(), existingUser.getRole())).thenReturn("testtoken");

        LoginResponseDto responseDto = userService.login(requestDto);

        assertNotNull(responseDto);
        assertEquals("testuser", responseDto.getUserId());
        assertEquals("test@example.com", responseDto.getEmail());
        assertEquals("Test User", responseDto.getName());
        assertEquals("testtoken", responseDto.getToken());
    }

    @Test
    void testLogin_UserNotFound() {
        LoginRequestDto requestDto = new LoginRequestDto();

        when(userRepository.findByUserId(requestDto.getUserId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.login(requestDto));
    }

}
