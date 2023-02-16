package com.example.rod.answer.controller;

import com.example.rod.answer.dto.AnswerRequestDto;
import com.example.rod.answer.dto.AnswerWithCommentsDto;
import com.example.rod.answer.dto.AnswerResponseDto;
import com.example.rod.answer.service.AnswerService;
import com.example.rod.security.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AnswerController {

    private final AnswerService answerService;

    // 질문에 대한 답변 작성
    @PostMapping("/questions/{questionId}/answers")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAnswer(@PathVariable Long questionId, @RequestBody AnswerRequestDto answerRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        answerService.createAnswer(questionId, answerRequestDto, userDetails);
    }

    // 답변 수정
    @PutMapping("/answers/{answerId}")
    public void updateAnswer(@PathVariable Long answerId, AnswerRequestDto answerRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        answerService.updateAnswer(answerId, answerRequestDto, userDetails);
    }

    // 답변 삭제
    @DeleteMapping("/answers/{answerId}")
    public void deleteAnswer(@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        answerService.deleteAnswer(answerId, userDetails);
    }

    // 내가 한 답변 리스트 조회
    @GetMapping("/answers")
    public List<AnswerResponseDto> getMyAnswers
    (@RequestParam(defaultValue = "1") int page,
     @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return answerService.getMyAnswers(pageable, page, userDetails);
    }


    // 내 답변 상세조회
    @GetMapping("/answers/{answerId}")
    public AnswerWithCommentsDto getAnswer(@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return answerService.getAnswer(answerId, userDetails);
    }

}
