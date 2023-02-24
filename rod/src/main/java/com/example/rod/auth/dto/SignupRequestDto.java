package com.example.rod.auth.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignupRequestDto {
    @Pattern(regexp = "[a-z0-9]{4,10}", message = "사용자 이름은 알파벳 소문자, 숫자로 구성한 4~10자 사이로 입력해주세요.")
    @Size(min = 4,max = 10,message ="아이디는 4에서 10자 사이 입니다.")
    private String username;

    @Size(min = 8,max = 15,message ="비밀번호는 8에서 15자 사이 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()_=+|{};:,.<>/?]*$", message = "비밀번호는 알파벳 대소문자, 숫자로 구성한 8~15자 사이로 입력해주세요.")
    private String password;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 형식이 일치하지 않습니다.")
    private String phoneNumber;

    private String nickname;
    private boolean admin = false;
    private String adminToken = "";


}
