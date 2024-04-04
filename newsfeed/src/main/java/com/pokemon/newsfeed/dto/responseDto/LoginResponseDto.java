package com.pokemon.newsfeed.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private String userId;
    private String password;
    private String email;
    private String name;
    private String token;
}
