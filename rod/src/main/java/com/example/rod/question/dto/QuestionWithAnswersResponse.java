package com.example.rod.question.dto;

import com.example.rod.answer.dto.AnswerResponseDto;
import com.example.rod.answer.entity.Answer;
import com.example.rod.question.entity.Question;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionWithAnswersResponse {

    private String title;
    private String content;
    private List<AnswerResponseDto> answerWithComments;

    public QuestionWithAnswersResponse(String title, String content, List<AnswerResponseDto> answerWithComments) {
        this.title = title;
        this.content = content;
        this.answerWithComments = answerWithComments;
    }

}