package com.example.rod.auth.service;

import com.example.rod.auth.dto.SigninRequestDto;
import com.example.rod.auth.dto.SignupRequestDto;


public interface AuthService {
    void signUp(SignupRequestDto signupRequestDto);

    void signIn(SigninRequestDto signinRequestDto);
}
