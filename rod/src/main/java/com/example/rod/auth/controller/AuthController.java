/*
package com.example.rod.auth.controller;

import com.example.rod.auth.dto.SignupRequestDto;
import com.example.rod.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public void signUp(@RequestBody @Validated SignupRequestDto signupRequestDto){
        authService.signUp(signRequestDto);
    }

    @PostMapping("/signin")
    public void signIn(@RequestBody @Validated SigninRequestDto signinRequestDto){
        authService.signIn(signinRequestDto);
    }
}
*/
