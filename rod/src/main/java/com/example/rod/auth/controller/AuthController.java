
package com.example.rod.auth.controller;

import com.example.rod.admin.service.AdminService;
import com.example.rod.auth.dto.SigninRequestDto;
import com.example.rod.auth.dto.SignupRequestDto;
import com.example.rod.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.util.LinkedMultiValueMap;
import reactor.util.MultiValueMap;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AdminService adminService;

    @PostMapping("/auth/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Validated SignupRequestDto signupRequestDto){
        authService.signUp(signupRequestDto);
    }

    @PostMapping("/auth/signin")
    @ResponseStatus(HttpStatus.OK)
    public void signIn(@RequestBody SigninRequestDto signinRequestDto, HttpServletResponse response){
        authService.signIn(signinRequestDto, response);
    }

//    @GetMapping("/auth/kakao/callback")
//    public @ResponseBody String kakaoCallback(String code){
//
//        RestTemplate rt = new RestTemplate();
//
//        //httpheader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //Httpbody 오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", "de816dd94079b68eba20785daa121afc");
//        params.add("redirect_uri", "https://spring-profile-image-bucket.s3.ap-northeast-2.amazonaws.com/frontend/static/index.html");
//        params.add("code", code);
//
//
//        //httpheader와 httpbody를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(params, headers);
//
//        //http 요청하기 - post - response 변수의 응답 받음
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.GET,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        return "토큰 요청 완료" + response;
//    }

}

