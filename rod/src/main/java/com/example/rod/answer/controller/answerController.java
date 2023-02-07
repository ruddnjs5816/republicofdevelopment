package com.example.rod.answer.controller;

import com.example.rod.answer.dto.answerRequestDto;
import com.example.rod.answer.dto.answerResponseDto;
import com.example.rod.answer.service.answerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class answerController {

    private final answerService answerservice;

    // 답변 작성
    @PostMapping("/questions/answers")
    public answerResponseDto createAnswer(@RequestBody answerRequestDto answerRequestDto) {
        return answerservice.createAnswer(answerRequestDto);
    }

    // 답변 수정
    @PutMapping("/answers/{answerId}")
    public answerResponseDto updateAnswer(@PathVariable Long answerId, answerRequestDto answerRequestDto) {
        return answerservice.updateAnswer(answerId, answerRequestDto);
    }

    // 답변 삭제
    @DeleteMapping("/answers/{answerId}")
    public void deleteAnswer(@PathVariable Long answerId) {
        answerservice.deleteAnswer(answerId);
    }

    // 내 답변 리스트조회
    @GetMapping("/answers")
    public List<answerResponseDto> getListAnswer() {
        return answerservice.getListAnswer();
    }


    // 내 답변 상세조회
    @GetMapping("/answers/{answerId}")
    public answerResponseDto getAnswer(@PathVariable Long answerId) {
        return answerservice.getAnswer(answerId);
    }

}
