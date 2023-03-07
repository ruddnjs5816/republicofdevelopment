package com.example.rod.oauth2.controller;

//import com.example.rod.oauth2.service.CustomOAuth2UserService;
//import com.example.rod.oauth2.service.KakaoService;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

}