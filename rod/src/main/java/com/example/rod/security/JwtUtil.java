package com.example.rod.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {



    private static final Long TOKEN_VALID_TIME = 1000L * 60 * 30; //토큰 유효시간
    private long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 7; //refresh 토큰 기한 7일
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_KEY = "auth";

    private final UserDetailsService userDetailsService;


    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes()));

    }

    //토큰 생성
    public String createToken(String user){
        Claims claims = Jwts.claims().setSubject(id);
        Date now = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now) // 발급시간
                        .setExpiration(new Date(now.getTime() + tokenValidTime))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact();
    }

    //refreshtoken 생성
    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, "bdhan")
                .compact();
    }

    //토큰으로 인증객체(Authentication) 얻기
    public Authentication getAuthentication(String token){

        return null;
    }
}
