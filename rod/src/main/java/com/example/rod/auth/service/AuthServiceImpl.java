package com.example.rod.auth.service;

import com.amazonaws.endpointdiscovery.Constants;
import com.example.rod.auth.dto.SigninRequestDto;
import com.example.rod.auth.dto.SignupRequestDto;
//import com.example.rod.oauth2.dto.OAuthAttributes;
import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.security.exception.CustomException;
import com.example.rod.security.jwt.JwtUtil;
import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserGrade;
import com.example.rod.user.entity.UserRole;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.example.rod.security.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String PROVIDER = "provider";
    private static final String PROVIDERID = "providerid";
    private static final String EMAIL = "email";
    private static final String ADDRESS = "서울특별시 서초구 반포동";

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Override
    @Transactional
    public void signUp(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String nickname = signupRequestDto.getNickname();
        String phoneNumber = signupRequestDto.getPhoneNumber();
        String encodedPassword = passwordEncoder.encode(password);
        Integer point = 0;
        Integer rating = 0;
        UserGrade userGrade = UserGrade.BRONZE;
        String adminToken = signupRequestDto.getAdminToken();

        // 회원 중복 확인

        validateUsername(username);

        // 사용자 ROLE(권한) 확인
        UserRole userRole = UserRole.USER;
        if (signupRequestDto.isAdmin()) {
            if (validateAdminToken(adminToken)) {
                userRole = UserRole.ADMIN;
            }
        }

        User user = User.builder()
                .username(username)
                .nickname(nickname)
                .role(userRole)
                .phoneNumber(phoneNumber)
                .grade(userGrade)
                .point(point)
                .rating(rating)
                .password(encodedPassword)
                .build();
        userRepository.save(user);

    }

    @Override
    @Transactional(readOnly = true)
    public void signIn(SigninRequestDto signinRequestDto, HttpServletResponse response) {
        String username = signinRequestDto.getUsername();
        String password = signinRequestDto.getPassword();

        // 회원인지 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        validatePassword(password, user.getPassword());

        //AUTHORIZATION_HEADER: KEY 값
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }

    @Override
    public Optional<Object> findByProviderAndOauthEmail(String provider, String email) {
        return Optional.empty();
    }

    @Transactional
    public void validatePassword(String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    @Transactional
    public boolean validateAdminToken(String adminToken) {
        if (!adminToken.matches(secretKey)) {
            throw new IllegalArgumentException("토큰이 일치하지 않습니다.");
        }
        return true;
    }

//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
//        // 동의 및 계속하기를 통해 발급된 {localhost}/login/oauth2/code/{provider}/code={accesstoken} 를 통해
//        // 발급된 토큰을 security가 해당 url로 온 요청을 캐치하여 OAuth2UserRequest 객체를 생성해서 정보를 담아 보내준다.
//        // 해당 토큰을 이용해서 DefaultOAuth2UserService의 loadUser에서 각각의 provider를 통해 유저 정보 요청을 보내준다.
//
//        OAuth2User user = new DefaultOAuth2UserService().loadUser(request);
//
//        String registrationId = request.getClientRegistration().getRegistrationId();
//
//        Map<String, Object> attributes = OAuthAttributes.extract(registrationId, user.getAttributes());
//
//        String provider = attributes.get(PROVIDER).toString();
//        String providerId = attributes.get(PROVIDERID).toString();
//
//        String nickname = provider + "_" + providerId;
//
//        String uuid = UUID.randomUUID().toString().substring(0, 6);
//        String password = passwordEncoder.encode(Constants.STANDARD_OAUTH2_PASS + uuid);
//
//        String email = attributes.get(EMAIL).toString();
//
//        User user = authService.findByProviderAndOauthEmail(provider, email)
//                .orElseGet(
//                        () -> createNewMember(email, nickname, password, provider)
//                );
//
//        return new UserDetailsImpl(user, attributes);
//    }
//
//    private User createNewMember(String email, String nickname, String password, String provider) {
//        // email, password, nickname, provier
//        User user = authService.saveUser(User.oauth2Register().email(email).nickname(nickname).password(password).provider(provider).oauthEmail(email).build());
//        locationService.saveLocation(Location.forNewMember().member(member).address(ADDRESS).vectorX(0).vectorY(0).build());
//
//        return user;
//    }
}
