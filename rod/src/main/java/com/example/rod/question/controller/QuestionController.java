package com.example.rod.question.controller;

import com.example.rod.question.dto.*;
import com.example.rod.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateQuestionResponse createQuestion(@RequestBody QuestionRequest questionRequest/* @AuthenticationPrincipal UserDetailsImpl userDetails)*/){

        CreateQuestionResponse createQuestionResponse = questionService.createQuestion(questionRequest /* , userDetails.getUser() */);

        return createQuestionResponse;
    }

    @GetMapping("/my-questions/{userId}")   // Security 없는 상황에서 테스트를 위해 임의로 userId를 PathVariable로 사용. -> 시큐리티 추가되면, URL 수정 필요!
    @ResponseStatus(HttpStatus.OK)
    public List<GetQuestionResponse> getQuestions(@PathVariable Long userId /* @AuthenticationPrincipal userDetailsImpl userDetails */) {

        List<GetQuestionResponse> questionResponseList = questionService.getQuestions(userId);/* String userId );*/ // Security 가 해줄거야..

        return questionResponseList;
    }


    @PatchMapping("/questions/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public PatchQuestionResponse changeQuestion(@PathVariable Long questionId, @RequestBody PatchQuestionRequest patchQuestionRequest){
        PatchQuestionResponse patchQuestionResponse = questionService.changeQuestion(questionId, patchQuestionRequest);
        return patchQuestionResponse;
    }







}
