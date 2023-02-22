package com.example.rod.init;

import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserGrade;
import com.example.rod.user.entity.UserRole;
import com.example.rod.user.repository.UserRepository;
import com.example.rod.user.service.UserService;
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

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String username = "bdhan";
        String name = "한병두";
        String encodedPassword = passwordEncoder.encode("pass");
        Integer rating = 0;
        Integer point = 0;
        String phonenumber = "010";
        UserGrade userGrade = UserGrade.BRONZE;
        UserRole userRole = UserRole.USER;

        User byeongdoo = User.builder()
                .username(username)
                .name(name)
                .role(userRole)
                .phoneNumber(phonenumber)
                .grade(userGrade)
                .point(point)
                .rating(rating)
                .password(encodedPassword)
                .build();
        userRepository.save(byeongdoo);


//        User byeongdoo = new User(
////                "byeongdoo", "한병두", passwordEncoder.encode("pass"), 0L, "01045078143", UserGrade.BRONZE
//        );
//        byeongdoo.setUserId(1L);
//        byeongdoo.setUsername("bdhan");
//        byeongdoo.setName("한병두");
//        byeongdoo.setPassword(passwordEncoder.encode("pass"));
//        byeongdoo.setRating(0);
//        byeongdoo.setPoint(0);
//        byeongdoo.setPhoneNumber("010");
//        byeongdoo.setGrade(UserGrade.BRONZE);

    }
}
