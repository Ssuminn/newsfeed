package com.pokemon.newsfeed.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokemon.newsfeed.dto.requestDto.LoginRequestDto;
import com.pokemon.newsfeed.entity.UserRoleEnum;
import com.pokemon.newsfeed.security.UserDetailsImpl;
import com.pokemon.newsfeed.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter (JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
//        setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication (
            HttpServletRequest req,
            HttpServletResponse res
    ) throws AuthenticationException {
        log.info("로그인 시도");

        try {
            LoginRequestDto requestDto = new ObjectMapper()
                    .readValue(req.getInputStream(), LoginRequestDto.class);
            // LoginRequestDto를 Controller에 보내지기 전에 여기서 받아서 사용

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUserId(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication authResult)
        throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 토큰 생성");
        String userId = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(userId, role);
        res.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            AuthenticationException failed
    ) throws IOException, ServletException {
        log.info("로그인 실패");
        res.setStatus(401);
    }
}

/*
    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication authResult)
        throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 토큰 생성");
        String userId = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(userId, role);
        jwtUtil.addJwtToCookie(token, res);
    }
 */