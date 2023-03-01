package com.example.rod.user.dto;

import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserGrade;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InfoResponseDto {

    private String username;

    private String nickname;

    private Integer point;

    private String phoneNumber;

    private UserGrade grade;

    private Integer rating;

    private String introduce;

    private String githubAddress;

    public InfoResponseDto(User user) {
        this.username = user.getUsername();
        this.point = user.getPoint();
        this.phoneNumber = user.getPhoneNumber();
        this.grade = user.getGrade();
        this.rating = user.getRating();
        this.introduce = user.getIntroduce();
        this.githubAddress = user.getGithunAddress();
    }
}
