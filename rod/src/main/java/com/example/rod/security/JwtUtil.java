/*

package com.example.rod.security;

import com.example.rod.user.entity.RoleType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jdk.dynalink.beans.StaticClass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private static final Long TOKEN_VALID_TIME = 1000L * 60 * 30; //토큰 유효시간
    private static final Long REFRESHTOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 7; //refresh 토큰 기한 7일
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
    public String createToken(String username, RoleType role){
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now) // 발급시간
                        .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();
    }

    //refreshtoken 생성
    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESHTOKEN_VALID_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
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
                    .parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e){
            log.error("토큰이 만료되었습니다.");
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e){
            log.error("Token Error!");
        }
        return false;
    }

    //토큰에서 사용자정보 가져오기
    protected Claims getUserInfoFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    //토큰으로 인증객체(Authentication) 얻기
    public Authentication takeAuthentication(String username){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
    }
}

*/
