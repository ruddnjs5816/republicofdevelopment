package com.example.rod.admin.service;

import com.example.rod.admin.dto.AdminSigninRequestDto;
import com.example.rod.admin.dto.AdminSignupRequestDto;
import com.example.rod.admin.entity.Admin;
import com.example.rod.admin.repository.AdminRepository;
import com.example.rod.auth.dto.SigninRequestDto;
import com.example.rod.security.exception.CustomException;
import com.example.rod.security.jwt.JwtUtil;
import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import static com.example.rod.security.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${jwt.secret.key}")
    private String secretKey;

    //admin 회원가입
    @Override
    @Transactional
    public void signUp(AdminSignupRequestDto adminSignupRequestDto) {
        String adminName = adminSignupRequestDto.getAdminName();
        String adminPassword = passwordEncoder.encode(adminSignupRequestDto.getAdminPassword());
        UserRole role = UserRole.ADMIN;
        //admin name 중복검사
        validateAdminName(adminName);

        //new admin 생성
        Admin admin = Admin.builder()
                .adminName(adminName)
                .adminPassword(adminPassword)
                .role(role)
                .build();

        //admin repository에 저장
        adminRepository.save(admin);
    }

    //admin 로그인
    @Override
    @Transactional
    public void signIn(AdminSigninRequestDto adminSigninRequestDto, HttpServletResponse response) {
        String adminName = adminSigninRequestDto.getAdminName();
        String adminPassword = passwordEncoder.encode(adminSigninRequestDto.getAdminPassword());

        // ADMIN 확인
        Admin admin = adminRepository.findByAdminName(adminName).orElseThrow(
                () -> new CustomException(NOT_FOUND_ADMIN)
        );

        //비밀번호 일치 확인
        validatePassword(adminPassword, admin.getAdminPassword());

        //AUTHORIZATION_HEADER: KEY 값
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(admin.getAdminName(), admin.getRole()));
    }

    //admin name 검증
    @Transactional
    public void validateAdminName(String adminName) {
        if(adminRepository.existsAdminByAdminName(adminName)){
            throw new IllegalArgumentException("이미 존재하는 ADMIN ID입니다.");
        }
    }

    //admin password 검증
    @Transactional
    public void validatePassword(String password, String encodedPassword){
        if(!passwordEncoder.matches(password, encodedPassword)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

}
