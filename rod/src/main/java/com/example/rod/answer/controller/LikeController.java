package com.example.rod.answer.controller;

import com.example.rod.answer.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService LikeService;

    @PostMapping("/answer/{answerId}")
    public void likeAnswer(@PathVariable Long answerId){
       LikeService.likeAnswer(answerId);
    }
}
