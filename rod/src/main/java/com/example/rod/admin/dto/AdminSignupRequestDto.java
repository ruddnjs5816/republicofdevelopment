package com.example.rod.admin.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class AdminSignupRequestDto {
    private String username;
    private String password;
    private String adminToken;
}
