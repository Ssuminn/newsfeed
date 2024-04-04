package com.pokemon.newsfeed.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotBlank
    private String email;
    @NotBlank
    private String name;

    public SignupRequestDto(String UserId, String password, String email, String name) {
        this.userId = UserId;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public SignupRequestDto() {
        
    }
}

