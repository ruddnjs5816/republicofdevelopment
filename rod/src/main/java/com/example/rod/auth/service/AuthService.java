package com.example.rod.auth.service;

public interface AuthService {
    void signUp(SignupRequestDto signupRequestDto);

    void signIn(SigninRequestDto signinRequestDto);
}
