package com.pokemon.newsfeed.dto.responseDto;

import lombok.Getter;

@Getter
public class ProfileResponseDto {

    private String name;
    private String userId;
    private String email;
    private String password;

    public ProfileResponseDto(String name, String userId, String email, String password) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.password = password;
    }
}
