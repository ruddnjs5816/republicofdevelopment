package com.example.rod.oauth2.dto;

import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserGrade;
import com.example.rod.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;


@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String username;
    private String nickname;
    private String email;
    private String imageUrl;
    private Integer point;
    private String phoneNumber;
    private Integer rating;
    private UserGrade grade;
    private UserRole role;
    private String filename;
    private String introduce;
    private String githubAddress;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
                           String username, String nickname, String email,
                           String imageUrl, Integer point, String phoneNumber,
                           Integer rating, UserGrade grade, UserRole role,
                           String filename, String introduce, String githubAddress) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
        this.point = point;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.grade = grade;
        this.role = role;
        this.filename = filename;
        this.introduce = introduce;
        this.githubAddress = githubAddress;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        // 여기서 네이버와 카카오 등 구분 (ofNaver, ofKakao)
        if("naver".equals(registrationId)){
            return ofNaver(userNameAttributeName, attributes);
        } else if("kakao".equals(registrationId)){
            return ofKakao(userNameAttributeName, attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .username((String) attributes.get("sub"))
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .imageUrl((String) attributes.get("imageUrl"))
                .nickname((String) attributes.get("nickname"))
                .imageUrl((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .username((String) attributes.get("sub"))
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .imageUrl((String) attributes.get("imageUrl"))
                .nickname((String) attributes.get("nickname"))
                .imageUrl((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> account = (Map<String, Object>) attributes.get("profile");
        return OAuthAttributes.builder()
                .username((String) attributes.get("sub"))
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .imageUrl((String) attributes.get("imageUrl"))
                .nickname((String) attributes.get("nickname"))
                .imageUrl((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .username(username)
                .email(email)
                .imageUrl(imageUrl)
                .role(UserRole.USER)
                .point(0)
                .rating(0)
                .build();
    }

}