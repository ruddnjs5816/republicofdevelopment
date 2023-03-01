package com.example.rod.user.dto;

import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserGrade;
import lombok.Getter;

@Getter
public class OtherInfoResponseDto {
    public OtherInfoResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.grade = user.getGrade();
        this.rating = user.getRating();
        this.introduce = user.getIntroduce();
        this.githubAddress = user.getGithunAddress();
    }

    private String username;

    private String nickname;
    private UserGrade grade;

    private Integer rating;

    private String introduce;

    private String githubAddress;

}
