package com.example.rod.auth.service;

import com.example.rod.auth.dto.SigninRequestDto;
import com.example.rod.auth.dto.SignupRequestDto;
import com.example.rod.security.exception.CustomException;
import com.example.rod.security.jwt.JwtUtil;
import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserGrade;
import com.example.rod.user.entity.UserRole;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import static com.example.rod.security.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void signUp(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String name = signupRequestDto.getName();
        String phoneNumber = signupRequestDto.getPhoneNumber();
        String encodedPassword = passwordEncoder.encode(password);
        Integer point = 0;
        Integer rating = 0;
        UserGrade userGrade = UserGrade.BRONZE;

        // 회원 중복 확인
//        Optional<User> found = userRepository.findByUsername(username);
//        if (found.isPresent()) {
//            throw new CustomException(DUPLICATED_USERNAME);
//        }
        validateUsername(username);

        // 사용자 ROLE(권한) 확인
        UserRole userRole = UserRole.USER;

        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(("${jwt.secret.key}"))) {
                throw new CustomException(INVALID_TOKEN);
            }
            userRole = UserRole.ADMIN;
        }

//        User user = signupRequestDto.toEntity(passwordEncoder.encode(signupRequestDto.getPassword()), role);
//        User user = new User(username, name, encodedPassword, point, phoneNumber, rating, userGrade, userRole);


        User user = User.builder()
                .username(username)
                .name(name)
                .role(userRole)
                .phoneNumber(phoneNumber)
                .grade(userGrade)
                .point(point)
                .rating(rating)
                .password(encodedPassword)
                .build();
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public void signIn(SigninRequestDto signinRequestDto, HttpServletResponse response) {
        String username = signinRequestDto.getUsername();
        String password = signinRequestDto.getPassword();

        // 회원인지 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        validatePassword(password, user.getPassword());

        //AUTHORIZATION_HEADER: KEY 값
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }

    @Transactional
    public void validatePassword(String password, String encodedPassword){
        if(!passwordEncoder.matches(password, encodedPassword)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


    @Transactional
    public void validateUsername(String username) {
        if(userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }
}
