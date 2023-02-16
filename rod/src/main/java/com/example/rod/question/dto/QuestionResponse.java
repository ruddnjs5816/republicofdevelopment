package com.example.rod.question.dto;

import com.example.rod.answer.entity.Answer;
import com.example.rod.question.entity.Question;
import lombok.Getter;


@Getter
public class QuestionResponse {

    private Long questionId;

    private String title;
    private String content;


    public QuestionResponse(Long questionId, String title, String content) {
        this.questionId = questionId;
        this.title = title;
        this.content = content;
    }

}
