package com.example.rod.auth.service;

import com.example.rod.auth.dto.SigninRequestDto;
import com.example.rod.auth.dto.SignupRequestDto;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;


public interface AuthService {
    void signUp(SignupRequestDto signupRequestDto);

    @Transactional(readOnly = true)
    void signIn(SigninRequestDto signinRequestDto, HttpServletResponse response);
}
