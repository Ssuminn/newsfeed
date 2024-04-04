package com.pokemon.newsfeed.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserUpdateDto {

    @NotBlank
    private String name;
    @NotBlank
    private String userId;
    @NotBlank
    private String email;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "영대소문자와 숫자만, 8~15길이만 허용")
    @NotBlank
    private String password;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "영대소문자와 숫자만, 8~15길이만 허용")
    @NotBlank
    private String confirmPassword;
}
