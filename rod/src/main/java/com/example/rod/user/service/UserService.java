package com.example.rod.user.service;

import com.example.rod.security.exception.CustomException;
import com.example.rod.security.jwt.JwtUtil;
import com.example.rod.user.dto.InfoResponseDto;
import com.example.rod.user.dto.LoginRequestDto;
import com.example.rod.user.dto.SignupRequestDto;
import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserRole;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

import static com.example.rod.security.exception.ErrorCode.*;

@Service
@Validated //다른 계층에서 파라미터를 검증
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;  //의존성 주입(DI) --> jwtUtil.class 에서 @Component 로 빈을 등록했기때문에 가능
    private final PasswordEncoder passwordEncoder;

    // 회원가입 구현
    @Transactional

    public void signup(@Valid SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new CustomException(DUPLICATED_USERNAME);
        }

        // 사용자 ROLE(권한) 확인
        UserRole role = UserRole.USER;

        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(("${jwt.secret.key}"))) {
                throw new CustomException(INVALID_TOKEN);
            }
            role = UserRole.ADMIN;
        }

        User user = signupRequestDto.toEntity(passwordEncoder.encode(signupRequestDto.getPassword()), role);
        userRepository.save(user);
    }

    //로그인 구현
    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(NOT_MATCH_INFORMATION);
        }

        //AUTHORIZATION_HEADER: KEY 값
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }


}
