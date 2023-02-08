/*
package com.example.rod.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Setter
@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

//    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    private long tokenValidTime = 1000L * 60 * 30; //토큰 유효시간
    private long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 7; //refresh 토큰 기한 7일
    private static final String BEARER_PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //토큰 생성
    public String createToken(String id){
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
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //토큰으로 인증객체(Authentication) 얻기
    public Authentication getAuthentication(String token){

        return null;
    }
}
*/
