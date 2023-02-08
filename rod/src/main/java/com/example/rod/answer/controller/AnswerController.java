/*
package com.example.rod.answer.controller;

import com.example.rod.answer.dto.AnswerRequestDto;
import com.example.rod.answer.dto.AnswerResponseDto;
import com.example.rod.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AnswerController {

    private final AnswerService answerservice;

    // 답변 작성
    @PostMapping("/questions/answers")
    public AnswerResponseDto createAnswer(@RequestBody AnswerRequestDto answerRequestDto) {
        return answerservice.createAnswer(answerRequestDto);
    }

    // 답변 수정
    @PutMapping("/answers/{answerId}")
    public AnswerResponseDto updateAnswer(@PathVariable Long answerId, AnswerRequestDto answerRequestDto) {
        return answerservice.updateAnswer(answerId, answerRequestDto);
    }

    // 답변 삭제
    @DeleteMapping("/answers/{answerId}")
    public void deleteAnswer(@PathVariable Long answerId) {
        answerservice.deleteAnswer(answerId);
    }

    // 내 답변 리스트조회
    @GetMapping("/answers")
    public List<AnswerResponseDto> getListAnswer() {
        return answerservice.getListAnswer();
    }


    // 내 답변 상세조회
    @GetMapping("/answers/{answerId}")
    public AnswerResponseDto getAnswer(@PathVariable Long answerId) {
        return answerservice.getAnswer(answerId);
    }

}
*/
