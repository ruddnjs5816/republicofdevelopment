package com.example.rod.admin.controller;

import com.example.rod.admin.dto.AdminSigninRequestDto;
import com.example.rod.admin.service.AdminServiceImpl;
import com.example.rod.admin.dto.AdminSignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminService;

    // admin 회원가입
    @PostMapping("/admin/auth/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void adminSignup(@RequestBody AdminSignupRequestDto adminSignupDto){
        adminService.signUp(adminSignupDto);
    }

    @PostMapping("/admin/auth/signin")
    @ResponseStatus(HttpStatus.OK)
    public void adminSignin(@RequestBody AdminSigninRequestDto adminSigninDto, HttpServletResponse response){
        adminService.signIn(adminSigninDto, response);
    }
}
