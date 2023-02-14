package com.example.rod.init;

import com.example.rod.user.entity.GradeType;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class InitData implements ApplicationRunner {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        User byeongdoo = new User(
//                "byeongdoo", "한병두", passwordEncoder.encode("pass"), 0L, "01045078143", GradeType.BRONZE
        );
        byeongdoo.setUserId(1L);
        byeongdoo.setUsername("bdhan");
        byeongdoo.setName("한병두");
        byeongdoo.setPassword(passwordEncoder.encode("pass"));
        byeongdoo.setRating(0L);
        byeongdoo.setPoint(0L);
        byeongdoo.setPhonenumber("010");
        byeongdoo.setGrade(GradeType.BRONZE);

        userRepository.save(byeongdoo);
    }
}
