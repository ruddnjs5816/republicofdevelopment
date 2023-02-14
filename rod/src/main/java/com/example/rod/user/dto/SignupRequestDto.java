package com.example.rod.user.dto;

import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserGrade;
import com.example.rod.user.entity.UserRole;
import lombok.Getter;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignupRequestDto {

    @Size(min = 4,max = 12,message ="아이디는 4에서 12자 사이 입니다.")
    @Pattern(regexp = "[a-z0-9]*$",message = "아이디 형식이 일치하지 않습니다.")
    private String username;

    @Size(min = 8,max = 15,message ="비밀번호는 8에서 15자 사이 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()_=+|{};:,.<>/?]*$",message = "비밀번호 형식이 일치하지 않습니다.")
    private String password;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 형식이 일치하지 않습니다.")
    private String phoneNumber;

    private boolean admin = false;

    private String adminToken = "";

    public User toEntity(String password, UserRole role){
        return User.builder()
                .username(username)
                .password(password)
                .point(0)
                .phoneNumber(phoneNumber)
                .grade(UserGrade.BRONZE)
                .role(role)
                .build();
    }

}