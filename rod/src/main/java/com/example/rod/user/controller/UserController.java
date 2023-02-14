package com.example.rod.user.controller;

import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.user.dto.InfoResponseDto;
import com.example.rod.user.dto.LoginRequestDto;
import com.example.rod.user.dto.MsgResponseDto;
import com.example.rod.user.dto.SignupRequestDto;
import com.example.rod.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;


    // 회원가입 구현
    @PostMapping("/signup")
    public ResponseEntity<MsgResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return ResponseEntity.ok(new MsgResponseDto("회원가입 완료", HttpStatus.OK.value()));
    }

    // 로그인 구현
    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<MsgResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return ResponseEntity.ok(new MsgResponseDto("로그인 완료",HttpStatus.OK.value()));
    }
}