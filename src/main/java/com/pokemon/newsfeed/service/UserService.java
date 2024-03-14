package com.pokemon.newsfeed.service;

import com.pokemon.newsfeed.dto.requestDto.LoginRequestDto;
import com.pokemon.newsfeed.dto.requestDto.SignupRequestDto;
import com.pokemon.newsfeed.dto.requestDto.UserUpdateDto;
import com.pokemon.newsfeed.dto.responseDto.LoginResponseDto;
import com.pokemon.newsfeed.dto.responseDto.ProfileResponseDto;
import com.pokemon.newsfeed.entity.User;

public interface UserService {
    void signup(SignupRequestDto requestDto);
    LoginResponseDto login(LoginRequestDto requestDto);
    void deleteUser(User user);
    ProfileResponseDto getProfile(Long userNum);
    ProfileResponseDto updateProfile(Long userNum, UserUpdateDto request);
}
