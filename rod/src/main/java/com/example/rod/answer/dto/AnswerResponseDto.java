package com.example.rod.answer.dto;

import com.example.rod.answer.entity.Answer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AnswerResponseDto {


    private Long questionId;
    private Long answerId;
    private String content;
    private int likes;

    private int commentCount;

    public AnswerResponseDto(Answer answer) {
        this.questionId = answer.getQuestion().getQuestionId();
        this.answerId = answer.getId();
        this.content = answer.getContent();
        this.likes = answer.getLikes();
        this.commentCount = answer.getComments().size();
    }
}
