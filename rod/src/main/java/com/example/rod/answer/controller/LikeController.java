package com.example.rod.answer.controller;

import com.example.rod.answer.service.AnswerLikeServiceImpl;
import com.example.rod.security.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final AnswerLikeServiceImpl AnswerLikeServiceImpl;

    @PostMapping("/answer/{answerId}")
    public void likeAnswer(@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails){
       AnswerLikeServiceImpl.likeAnswer(answerId, userDetails);
    }
}
