package com.example.rod;

import com.example.rod.auth.service.AuthService;
import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserGrade;
import com.example.rod.user.entity.UserRole;
import com.example.rod.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

@SpringBootTest
public class SignupTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private AuthService authService;

    @Test
    @DisplayName("회원가입 test")
    void signupTest() throws SQLException {
        // docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER='user' -e POSTGRES_DB=messenger --name postgres_boot -d postgres
        // docker exec -i -t postgres_boot bash
        // su - postgres
        // psql --username teasun --dbname messenger

        String dburl = "jdbc:postgresql://localhost:5432/messenger";
        String dbusername = "byeongdoo";
        String dbpassword = "pass";

        try(Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword)){
            String username = "bdhan";
            String nickname = "한병두";
            String encodedPassword = passwordEncoder.encode("pass");
            Integer rating = 0;
            Integer point = 0;
            String phonenumber = "010";
            UserGrade userGrade = UserGrade.BRONZE;
            UserRole userRole = UserRole.USER;

            User byeongdoo = User.builder()
                    .username(username)
                    .nickname(nickname)
                    .role(userRole)
                    .phoneNumber(phonenumber)
                    .grade(userGrade)
                    .point(point)
                    .rating(rating)
                    .password(encodedPassword)
                    .build();

            userRepository.saveAndFlush(byeongdoo);
        } catch (SQLException e){
            if (e.getMessage().equals("ERROR: relation \"user\" already exists")) {
                System.out.println("유저가 이미 존재합니다.");
            } else {
                throw new RuntimeException();
            }
        }
    }

    @Test
    @DisplayName("로그인 test")
    void loginTest() throws SQLException {

        signupTest();

        Optional<User> user = userRepository.findByUsername("bdhan");

        String dburl = "jdbc:postgresql://localhost:5432/messenger";
        String dbusername = "byeongdoo";
        String dbpassword = "pass";

        try(Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword)){
            String username = "bdhan";
            String nickname = "한병두";
            String encodedPassword = passwordEncoder.encode("pass");
            Integer rating = 0;
            Integer point = 0;
            String phonenumber = "010";
            UserGrade userGrade = UserGrade.BRONZE;
            UserRole userRole = UserRole.USER;

            User byeongdoo = User.builder()
                    .username(username)
                    .nickname(nickname)
                    .role(userRole)
                    .phoneNumber(phonenumber)
                    .grade(userGrade)
                    .point(point)
                    .rating(rating)
                    .password(encodedPassword)
                    .build();

            userRepository.saveAndFlush(byeongdoo);
        } catch (SQLException e){
            if (e.getMessage().equals("ERROR: relation \"user\" already exists")) {
                System.out.println("유저가 이미 존재합니다.");
            } else {
                throw new RuntimeException();
            }
        }
    }
}

