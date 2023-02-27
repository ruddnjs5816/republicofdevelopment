package com.example.rod.admin.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class AdminSignupRequestDto {
    private String adminName;
    private String adminPassword;
    private String adminToken;
}
