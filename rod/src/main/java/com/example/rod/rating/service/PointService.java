package com.example.rod.rating.service;

import com.example.rod.rating.dto.PointRequestDto;
import com.example.rod.rating.dto.PointResultDto;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;
    public PointResultDto plusPoint(Long id, PointRequestDto pointDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new  IllegalArgumentException("찾는 유저가 없습니다."));
        long plusPoint = user.getPoint() + pointDto.getPoint();
        return new PointResultDto(plusPoint);
    }

    public PointResultDto minusPoint(Long id, PointRequestDto pointDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new  IllegalArgumentException("찾는 유저가 없습니다."));
        long minusPoint = user.getPoint() - pointDto.getPoint();
        return new PointResultDto(minusPoint);
    }
}
