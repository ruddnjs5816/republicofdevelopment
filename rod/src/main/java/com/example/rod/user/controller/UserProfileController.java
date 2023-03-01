package com.example.rod.user.controller;

import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.profile.dto.ProfileRequestDto;
import com.example.rod.user.dto.MyInfoResponseDto;
import com.example.rod.user.dto.OtherInfoResponseDto;
import com.example.rod.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/auth")
public class UserProfileController {

    private final UserService userService;


    // 내 프로필 조회
    @GetMapping("/users/mypage")
    public MyInfoResponseDto getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getMyInfo(userDetails.getUser());
    }

    // 내 프로필 수정
    @PatchMapping("/users/mypage")
    public void editMyInfo(@RequestBody ProfileRequestDto profileRequestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.editMyInfo(profileRequestDto, userDetails);
    }

    // 타인 프로필 조회
    @GetMapping("/profiles/{userId}")
    public OtherInfoResponseDto getOtherInfo(@PathVariable Long userId){
        return userService.getOtherInfo(userId);
    }

}
