package com.example.rod.question.dto;

import com.example.rod.answer.dto.AnswerWithCommentsDto;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionWithAnswersResponse {

    private String title;
    private String content;
    private List<AnswerWithCommentsDto> answerWithComments;

    public QuestionWithAnswersResponse(String title, String content, List<AnswerWithCommentsDto> answerWithComments) {
        this.title = title;
        this.content = content;
        this.answerWithComments = answerWithComments;
    }

}