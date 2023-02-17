package com.example.rod.user.service;

import com.example.rod.security.exception.CustomException;
import com.example.rod.security.jwt.JwtUtil;
import com.example.rod.user.dto.InfoResponseDto;
import com.example.rod.user.dto.LoginRequestDto;
import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserRole;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

import static com.example.rod.security.exception.ErrorCode.*;

@Service
@Validated //다른 계층에서 파라미터를 검증
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 내 프로필 조회
    @Transactional(readOnly = true)
    public InfoResponseDto getMyInfo(User user) {
        InfoResponseDto info = new InfoResponseDto(user);
        return info;
    }

    // 내 프로필 수정
    public void editMyInfo(User user) {
        User savedUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new IllegalArgumentException("없습니다"));
//        savedUser.update(user.getUserId(), user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getPhoneNumber());

        String encPassword=passwordEncoder.encode(user.getPassword());
        savedUser.setUserId(user.getUserId());
        savedUser.setPassword(encPassword);
        savedUser.setUsername(user.getUsername());
        savedUser.setPhoneNumber(user.getPhoneNumber());

        userRepository.save(savedUser);

    }
}
