package com.pokemon.newsfeed.util;

import com.pokemon.newsfeed.entity.UserRoleEnum;
import com.pokemon.newsfeed.jwt.JwtAuthorizationFilter;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    // Header Key 값
    public static  final String AUTHORIZATION_HEADER = "Authorization";

    // 사용자 권한 값의 Key
    public static final String AUTHORIZATION_KEY = "auth"; // 권한 사용할 때 필요

    //Token 식별자
    public static final String BEARER_PREFIX = "Bearer";

    // 토큰 만료 시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value(("${jwt.secret.key}"))
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // todo: SignatureAlgorithm의 역할, 왜 사용하는지?

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken (String userId, UserRoleEnum role) {
        // 토큰 생성을 위한 메서드
        Date date = new Date();

        return BEARER_PREFIX + // bearer로 시작할 수 있게 하고
                Jwts.builder()
                        .setSubject(userId) // 사용자 식별 (id)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한 확인할 때 사용
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 토큰 만료 시간
                        .setIssuedAt(date) // 토큰 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
        // todo: return 내용 코드 분석하기
    }

    // header에서 JWT 가져오기
    public String getJwtFromHeader (HttpServletRequest req) {
//        JwtAuthorizationFilter에서 HttpServletRequest를 받아옴
        String bearerToken = req.getHeader(AUTHORIZATION_HEADER);
//        HttpServletRequest 여기에 있는 getHeader 메서드를 이용하여
//        AUTHORIZATION_HEADER 이 값으로 되어 있는 JWT 토큰을 가져옴
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            가져온 토큰 null인지 아닌지 문자열이 공백이 있는지와 bearer이걸로 시작하는지 확인
            return bearerToken.substring(7);
//            bearerToken을 순수한 토큰으로 뽑아내기 위해
            //todo: 공백 없이 substring으로 잘못 잘랐는데 잘 작동함

        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken (String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            // todo: 위 코드의 의미....
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT Signature, 유효하지 않는 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Token, 만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT Token, 지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT Claims is Empty, 잘못된 JWT 토큰입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken (String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}

/*
    // JWT Cookie에 저장
    public void addJwtToCookie (String token, HttpServletResponse res) {
        // todo: HttpServletResponse란?
        try {
            token = URLEncoder.encode(token, "UTF-8").replaceAll("\\+", "%20");
            // Cookie Value에는 공백이 들어갈 수 없어서 encoding 진행
            // todo: URLEncoder.encode 사용방법 utf-8로 맞추는 이유 replaceAll 사용하는 이유 \\+와 %20의 역할?

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
            // Name, Value
            cookie.setPath("/");
            // todo: 이 Path의 의미

            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            // todo: catch 파라미터 e는 사용하고 싶을 때 사용해도 되는건지?
            logger.error(e.getMessage());
        }
    }

    // JWT 토큰 substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            // todo: StringUtils.hasText란?
            return tokenValue.substring(7);
        }
        logger.error("NOT FOUND TOKEN");
        throw new NullPointerException("NOT FOUND TOKEN");
    }

    // HttpServletRequest에서 Cookie Value : JWT 가져오기
    public String getTokenFromRequest (HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
 */