package com.pokemon.newsfeed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean // 빈 등록
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
        // 비밀번호를 암호화 해주는 해시 함수 : BCrypt
    }
}
