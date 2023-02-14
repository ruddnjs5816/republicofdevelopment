package com.example.rod.user.controller;

import com.example.rod.user.dto.LoginRequestDto;
import com.example.rod.user.dto.MsgResponseDto;
import com.example.rod.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

}