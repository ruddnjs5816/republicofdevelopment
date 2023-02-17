package com.example.rod.user.controller;

import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.user.dto.InfoResponseDto;
import com.example.rod.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserProfileController {

    private final UserService userService;


    // 내 프로필 조회
    @GetMapping("/info")
    public InfoResponseDto getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getMyInfo(userDetails.getUser());
    }

    // 내 프로필 수정
    @PutMapping("/edit")
    public void editMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.editMyInfo(userDetails.getUser());


    }

    // 내 프로필 등록



}
