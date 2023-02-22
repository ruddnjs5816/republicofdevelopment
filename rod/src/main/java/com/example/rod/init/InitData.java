package com.example.rod.init;

import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserGrade;
import com.example.rod.user.entity.UserRole;
import com.example.rod.user.repository.UserRepository;
import com.example.rod.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.DelegatingBasicLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;


@Component
@RequiredArgsConstructor
@AutoConfiguration
//@Import(DBConfiguration.class)
public class InitData implements ApplicationRunner {

//    private final UserRepository userRepository;
//
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;

//    private final QuestionMapper questionMapper;

//    private final UserMapper userMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        String dburl = "jdbc:postgresql://localhost:5432/messenger";
//        String dbusername = "byeongdoo";
//        String dbpassword = "pass";
//        try(Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword)){
//            String username = "bdhan";
//            String name = "한병두";
//            String encodedPassword = passwordEncoder.encode("pass");
//            Integer rating = 0;
//            Integer point = 0;
//            String phonenumber = "010";
//            UserGrade userGrade = UserGrade.BRONZE;
//            UserRole userRole = UserRole.USER;
//
//            User byeongdoo = User.builder()
//                    .username(username)
//                    .name(name)
//                    .role(userRole)
//                    .phoneNumber(phonenumber)
//                    .grade(userGrade)
//                    .point(point)
//                    .rating(rating)
//                    .password(encodedPassword)
//                    .build();
//
//            userRepository.saveAndFlush(byeongdoo);
//        } catch (IllegalArgumentException e){
//            e.getMessage();
//        }


//        userMapper.selectUser(1L);


    }
}
