package com.example.rod.question.controller;

import com.amazonaws.services.cloudformation.model.Change;
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

import java.util.Optional;

@CrossOrigin(origins = "http://localhost")

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;


    // 질문 작성 API
    @PostMapping("/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestion(@RequestBody QuestionRequest questionRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        questionService.createQuestion(questionRequest, userDetails);
    }


    // 내 질문 리스트 조회 API
    @GetMapping("/my-questions")
    @ResponseStatus(HttpStatus.OK)
    public GetQuestionsResponse getMyQuestions(@RequestParam(defaultValue = "1") int page,
                                               @PageableDefault(size = 10, sort = "questionId", direction = Sort.Direction.ASC) Pageable pageable,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {

        GetQuestionsResponse myQuestionList = questionService.getMyQuestions(userDetails, pageable, page);

        return myQuestionList;
    }

    // 모든 질문 리스트 조회 API : 질문 제목 / 내용만 가져오는 API.
    @GetMapping("/questions/all")
    @ResponseStatus(HttpStatus.OK)
    public GetQuestionsResponse getQuestions(
            @RequestParam(defaultValue = "1") int page,
            @PageableDefault(size = 10, sort = "questionId", direction = Sort.Direction.ASC) Pageable pageable) {

        GetQuestionsResponse questionResponseList = questionService.getQuestions(pageable, page);
        return questionResponseList;
    }


    // 특정 질문 조회 API ( Question - Answer - Comment 전부 다 반환 )
    @GetMapping("/questions/specific/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionWithAnswersResponse getSpecificQuestion(@PathVariable Long questionId) {
        QuestionWithAnswersResponse questionResponse = questionService.getSpecificQuestion(questionId);
        return questionResponse;
    }


    // 질문의 답변 채택 API
    @PatchMapping("/questions/{questionId}/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    public void selectAnswerForQuestion(@PathVariable Long questionId, @PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        questionService.selectAnswerForQuestion(questionId, answerId, userDetails);
    }


    // 질문 수정 API
    @PutMapping("/questions/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public void changeQuestion(@PathVariable Long questionId, @RequestBody ChangeQuestionRequest changeQuestionRequest, @AuthenticationPrincipal UserDetailsImpl userDetails){
        questionService.changeQuestion(questionId, changeQuestionRequest, userDetails);
    }


    // 내 질문 삭제 API
    @DeleteMapping("/questions/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@PathVariable Long questionId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        questionService.deleteQuestion(questionId, userDetails);
    }

    // 질문 검색 API ( 제목, 유저 닉네임으로 검색 )
    @GetMapping("/questions/search")
    @ResponseStatus(HttpStatus.OK)
    public GetQuestionsResponse searchQuestion(@RequestParam("title") Optional<String> title,
                                                      @RequestParam("nickname") Optional<String> nickname,
                                                      @RequestParam("tagname") Optional<String> tagname,
                                                      @RequestParam(defaultValue = "1") int page,
                                                      @PageableDefault(size = 10, sort = "questionId", direction = Sort.Direction.ASC) Pageable pageable) {

        if (title.isPresent() || nickname.isPresent() || tagname.isPresent()) {
            return questionService.searchQuestion(title, nickname, tagname, page, pageable);
        }
        else {
            throw new IllegalArgumentException("제목 혹은 닉네임 혹은 태그이름을 매개변수로 전달해주어야 합니다.");
        }
    }


    // 질문에 이미지 업로드 API

    /*@PostMapping("/questions/upload")
    @ResponseStatus(HttpStatus.OK)
    public String uploadImage(@RequestParam("image")MultipartFile image, RedirectAttributes redirectAttributes) throws GetException {
        questionService.uploadImage(image);
        redirectAttributes.addFlashAttribute("message",
                "You successfully upload " + image.getOriginalFilename() + "!");
        return "redirect:/";
    }*/
}

