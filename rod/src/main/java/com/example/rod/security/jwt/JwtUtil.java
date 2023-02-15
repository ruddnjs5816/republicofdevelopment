

package com.example.rod.security.jwt;

import com.example.rod.security.SecurityExceptionResponse;
import com.example.rod.user.entity.UserGrade;
import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private static final Long TOKEN_VALID_TIME = 1000L * 60 * 60; //토큰 유효시간
    private static final Long REFRESHTOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 7; //refresh 토큰 기한 7일
    private static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";


    private final UserDetailsService userDetailsService;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 이 알고리즘을 사용해서 키 객체를 암호화할 것이다.


    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;

    @PostConstruct
    protected void init() {
//        key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes()));
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    //토큰 생성
    public String createToken(String username, UserRole userRole){
//        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, userRole)
                        .setIssuedAt(now) // 발급시간
                        .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))
                        .signWith(signatureAlgorithm, key)
                        .compact();
    }

    //refreshtoken 생성
    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESHTOKEN_VALID_TIME))
                .signWith(signatureAlgorithm,key)
                .compact();
    }

    //header 토큰 가져오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }

    //토큰 검증
    public boolean vaildateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    //토큰에서 사용자정보 가져오기
    public Claims getUserInfoFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    //토큰으로 인증객체(Authentication) 얻기
    public Authentication takeAuthentication(String username){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionResponse(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

