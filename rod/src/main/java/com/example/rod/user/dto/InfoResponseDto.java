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

    private int point;

    private String phoneNumber;

    private UserGrade grade;

    public InfoResponseDto(User user) {
        this.username = user.getUsername();
        this.point = user.getPoint();
        this.phoneNumber = user.getPhoneNumber();
        this.grade = user.getGrade();
    }
}
