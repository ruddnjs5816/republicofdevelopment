package com.example.rod.user.dto;

import com.example.rod.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    Long userId;
    String email;
    String nickname;
    @Setter
    String profileImageUrl;

    /**
     * 생성자
     */
    private UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }

    /**
     * 유저 객체를 DTO에 담아 반환해줍니다.
     *
     * @param user 유저 객체
     * @return UserResponseDto
     */
    public static UserResponseDto of(User user) {
        return new UserResponseDto(user);
    }
}