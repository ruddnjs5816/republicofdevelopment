package com.example.rod.question.dto;

import com.example.rod.answer.entity.Answer;
import com.example.rod.question.entity.Question;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
public class QuestionResponse {

    private Long questionId;

    private String title;

    private String nickname;

    // 만든 날짜
    private LocalDateTime createdAt;

    // 이 Question에 달린 답변의 수
    private Integer answerCount;


    @Builder
    public QuestionResponse(Long questionId, String title, String nickname, LocalDateTime createdAt, Integer answerCount) {
        this.questionId = questionId;
        this.title = title;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.answerCount = answerCount;
    }

}
