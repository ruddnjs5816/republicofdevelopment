package com.example.rod.user.service;

import com.example.rod.user.dto.InfoResponseDto;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated //다른 계층에서 파라미터를 검증
@RequiredArgsConstructor
public class UserProfileService {

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
