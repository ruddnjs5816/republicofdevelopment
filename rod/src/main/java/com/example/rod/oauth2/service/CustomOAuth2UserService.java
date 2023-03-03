package com.example.rod.oauth2.service;

import com.example.rod.oauth2.dto.OAuthAttributes;
import com.example.rod.oauth2.dto.SessionUser;
import com.example.rod.security.exception.CustomException;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 서비스 id (구글, 카카오, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user",
                new SessionUser(user)); // SessionUser (직렬화된 dto 클래스 사용)

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRole().getAuthority())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    // 유저 생성 및 수정 서비스 로직
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(
                        user1 -> user1.update(
                                attributes.getUsername(),
                                attributes.getImageUrl(),
                                attributes.getNickname()

                        )
                ).orElseThrow(
                        () -> new IllegalArgumentException("이메일이 등록되지 않은 계정입니다.")
                );
        userRepository.save(user);
        return user;
    }


//                .map(entity -> entity.update(
//                        attributes.getUsername(),
//                        attributes.getImageUrl()
//                ))
//                .orElse(
////                        attributes.toEntity()
//                        User.builder()
//                                .nickname(attributes.getNickname())
//                                .build()
//
//                );
                //구글로 회원가입 ->
//        return userRepository.save(user);

}