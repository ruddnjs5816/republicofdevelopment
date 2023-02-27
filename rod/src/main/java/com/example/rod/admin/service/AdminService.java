package com.example.rod.admin.service;

import com.example.rod.admin.dto.AdminSigninRequestDto;
import com.example.rod.admin.dto.AdminSignupRequestDto;

import javax.servlet.http.HttpServletResponse;

public interface AdminService {
//
    void signUp(AdminSignupRequestDto adminSignupRequestDto);

    void signIn(AdminSigninRequestDto adminSigninRequestDto, HttpServletResponse response);
}
