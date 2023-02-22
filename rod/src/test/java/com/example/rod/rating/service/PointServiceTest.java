package com.example.rod.rating.service;

import com.example.rod.user.entity.UserGrade;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PointServiceTest {

//    @Autowired
//    public PointService pointService;
    @Autowired
    private UserRepository userRepository;
    @Test
    void plus() {
        //given- 어떤 데이터가 주어졌을 때

//        User user1 = User.builder().username();


//        User user2 =
//                new User(1L,"김상순","나무","1111",2000L,"010", UserGrade.BRONZE);
//        userRepository.save(user2);
//
//        //when- 어떤 동작을 할 때
//        PointResultDto pointResultDto = pointService.plusPoint(user2.getUserId(), new PointRequestDto(3000L));
//
//
//        //then- 어떤 결과가 발생한다
//        Assertions.assertEquals(pointResultDto.getPoint(), 5000L);
    }

    @Test
    void 마이너스() {
        //given- 어떤 데이터가 주어졌을 때
        //when- 어떤 동작을 할 때
        //then- 어떤 결과가 발생한다

    }
}