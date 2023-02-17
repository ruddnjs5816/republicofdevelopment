package com.example.rod.question.controller;

import com.example.rod.question.dto.*;
import com.example.rod.question.service.QuestionService;
import com.example.rod.security.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;


    // 질문 작성 API
    @PostMapping("/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestion(@RequestBody QuestionRequest questionRequest, @AuthenticationPrincipal UserDetailsImpl userDetails){
        questionService.createQuestion(questionRequest,userDetails);
    }


    // 내 질문 리스트 조회 API
    @GetMapping("/my-questions/{userId}")   // Security 없는 상황에서 테스트를 위해 임의로 userId를 PathVariable로 사용. -> 시큐리티 추가되면, URL & 서비스 레이어 수정 필요!
    @ResponseStatus(HttpStatus.OK)
    public GetQuestionsResponse getMyQuestions(@PathVariable Long userId,
                                                 @RequestParam(defaultValue = "1") int page,
                                                 @PageableDefault(size = 10,
                                                 sort = "questionId",
                                                 direction = Sort.Direction.ASC) Pageable pageable
                                                /* @AuthenticationPrincipal userDetailsImpl userDetails */) {
        GetQuestionsResponse myQuestionList = questionService.getMyQuestions(userId, pageable, page);

        return myQuestionList;
    }

    // 모든 질문 리스트 조회 API : 질문 제목 / 내용만 가져오는 API.
    @GetMapping("/questions")
    @ResponseStatus(HttpStatus.OK)
    public GetQuestionsResponse getQuestions(
            @RequestParam(defaultValue = "1") int page,
            @PageableDefault(size = 10, sort = "questionId", direction = Sort.Direction.ASC) Pageable pageable/* @AuthenticationPrincipal userDetailsImpl userDetails */) {

        GetQuestionsResponse questionResponseList = questionService.getQuestions(pageable, page);    //  추후 페이징 처리 해줘야 함.
        return questionResponseList;
    }



    // 세부 질문 조회 API
    @GetMapping("/questions/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionWithAnswersResponse getSpecificQuestion(@PathVariable Long questionId){
        QuestionWithAnswersResponse questionResponse = questionService.getSpecificQuestion(questionId);
        return questionResponse;
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

    // 질문에 이미지 업로드 API
    @PostMapping("/questions/upload")
    @ResponseStatus(HttpStatus.OK)
    public String uploadImage(@RequestParam("image")MultipartFile image, RedirectAttributes redirectAttributes){
        questionService.uploadImage(image);
        redirectAttributes.addFlashAttribute("message",
                "You successfully upload " + image.getOriginalFilename() + "!");
        return "redirect:/";
    }
}
