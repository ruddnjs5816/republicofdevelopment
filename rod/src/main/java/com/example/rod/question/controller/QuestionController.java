package com.example.rod.question.controller;

import com.example.rod.question.dto.*;
import com.example.rod.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;


    // 질문 작성 API
    @PostMapping("/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestion(@RequestBody QuestionRequest questionRequest/* @AuthenticationPrincipal UserDetailsImpl userDetails)*/){
        questionService.createQuestion(questionRequest /* , userDetails.getUser() */);
    }

    // 내 질문 리스트 조회 API
    @GetMapping("/my-questions/{userId}")   // Security 없는 상황에서 테스트를 위해 임의로 userId를 PathVariable로 사용. -> 시큐리티 추가되면, URL & 서비스 레이어 수정 필요!
    @ResponseStatus(HttpStatus.OK)
    public List<GetQuestionResponse> getMyQuestions(@PathVariable Long userId /* @AuthenticationPrincipal userDetailsImpl userDetails */) {
        List<GetQuestionResponse> myQuestionList = questionService.getMyQuestions(userId);/* String userId );*/ // Security 가 해줄거야..
        return myQuestionList;
    }

    // 모든 질문 리스트 조회 API
    @GetMapping("/questions")
    @ResponseStatus(HttpStatus.OK)
    public List<GetQuestionResponse> getQuestions(/* @AuthenticationPrincipal userDetailsImpl userDetails */){
        List<GetQuestionResponse> questionResponseList = questionService.getQuestions();    //  추후 페이징 처리 해줘야 함.
        return questionResponseList;
    }

    @GetMapping("/questions/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public GetQuestionResponse getSpecificQuestion(@PathVariable Long questionId){
        GetQuestionResponse getQuestionResponse = questionService.getSpecificQuestion(questionId);
        return getQuestionResponse;
    }



    // 질문의 제목을 바꾸는 경우 API
    @PatchMapping("/questions/{questionId}/title")
    @ResponseStatus(HttpStatus.OK)
    public void changeQuestionTitle(@PathVariable Long questionId, @RequestBody PatchQuestionTitleRequest patchQuestionTitleRequest /* @AuthenticationPrincipal userDetailsImpl userDetails */){
        questionService.changeQuestionTitle(questionId, patchQuestionTitleRequest);
    }


    // 질문의 내용(content)를 바꾸는 경우 API
    @PatchMapping("/questions/{questionId}/content")
    @ResponseStatus(HttpStatus.OK)
    public void changeQuestionContent(@PathVariable Long questionId, @RequestBody PatchQuestionContentRequest patchQuestionContentRequest /* @AuthenticationPrincipal userDetailsImpl userDetails */){
        questionService.changeQuestionContent(questionId, patchQuestionContentRequest);
    }


    // 내 질문 삭제 API
    @DeleteMapping("/questions/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@PathVariable Long questionId/* @AuthenticationPrincipal userDetailsImpl userDetails */){
        questionService.deleteQuestion(questionId);
    }







}
