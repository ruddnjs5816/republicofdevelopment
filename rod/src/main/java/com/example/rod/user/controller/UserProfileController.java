package com.example.rod.user.controller;

import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.user.dto.InfoResponseDto;
import com.example.rod.user.dto.ProfileRequestDto;
import com.example.rod.user.entity.User;
import com.example.rod.user.service.UserProfileService;
import com.example.rod.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserProfileController {

    private final UserProfileService userProfileService;


    // 내 프로필 조회
    @GetMapping("/info")
    public InfoResponseDto getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userProfileService.getMyInfo(userDetails.getUser());
    }

    // 내 프로필 수정
    @PutMapping("/edit")
    public void editMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userProfileService.editMyInfo(userDetails.getUser());


    }

    // 내 프로필 등록



}
