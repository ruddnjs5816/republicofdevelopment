package com.example.rod.init;

import com.example.rod.admin.service.AdminService;
import com.example.rod.auth.service.AuthService;
import com.example.rod.mybatis.config.DBConfiguration;
import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserRole;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;


@Component
@RequiredArgsConstructor
@AutoConfiguration
@Import(DBConfiguration.class)
public class InitData implements ApplicationRunner {

    private final UserRepository userRepository;
    private final AdminService adminService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret.key}")
    private final String secretKey;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String dburl = "jdbc:postgresql://localhost:5432/messenger";
        String dbusername = "byeongdoo";
        String dbpassword = "pass";

        try(Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword)){
            String username = "admin";
            String encodedPassword = passwordEncoder.encode("root");

            User admin = User.builder()
                    .username(username)
                    .password(encodedPassword)
                    .role(UserRole.ADMIN)
                    .build();

            userRepository.save(admin);
        } catch (IllegalArgumentException e){
            e.getMessage().substring(0,1);
        }


//        userMapper.selectUser(1L);


    }
}
