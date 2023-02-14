package com.example.rod.rating.service;

import com.example.rod.rating.dto.RatingDto;
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
//        user.changeGrade(ratingDto.getRole());
        userRepository.save(user);
    }

    public void getGrade(Integer rating) {
        //rating에 해당하는 등급 여기서 리턴 받고
        //받은 등급으로 changeGrade 실행
    }
}
