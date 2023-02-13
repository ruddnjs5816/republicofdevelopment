package com.example.rod.rating.service;

import com.example.rod.rating.dto.RatingDto;
import com.example.rod.rating.repository.RatingRepository;
import com.example.rod.user.entity.RoleType;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final UserRepository userRepository;

    @Transactional
    public void changeGrade(Long id, RatingDto ratingDto) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new  IllegalArgumentException("찾는 유저가 없습니다."));
//        user.changeRole(ratingDto.getRole());
        userRepository.save(user);
    }
}
