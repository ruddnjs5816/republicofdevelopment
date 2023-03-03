package com.example.rod.oauth2.dto;

import com.example.rod.user.entity.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 직렬화 기능을 가진 User클래스
 */
@Getter
public class SessionUser implements Serializable {
    private String username;
    private String nickname;
    private String email;
    private String imageUrl;

    public SessionUser(User user){
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
    }
}