package com.example.rod.profile.dto;

import com.example.rod.user.entity.UserGrade;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class ProfileResponseDto {
    private String nickname;

    @Size(min = 8,max = 15,message ="비밀번호는 8에서 15자 사이 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()_=+|{};:,.<>/?]*$", message = "비밀번호는 알파벳 대소문자, 숫자로 구성한 8~15자 사이로 입력해주세요.")
    private String password;

    private String phoneNumber;
    private Long rating;
    private Long point;
    private UserGrade grade;
}
