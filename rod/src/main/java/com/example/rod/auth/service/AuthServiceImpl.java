package com.example.rod.auth.service;

import com.example.rod.auth.dto.SigninRequestDto;
import com.example.rod.auth.dto.SignupRequestDto;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signUp(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String name = signupRequestDto.getName();
        String phonenumber = signupRequestDto.getPhonenumber();
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, name, phonenumber);
        userRepository.save(user);
    }

    @Override
    public void signIn(SigninRequestDto signinRequestDto) {

    }
}
