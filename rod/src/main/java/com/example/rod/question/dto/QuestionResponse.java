package com.example.rod.question.dto;

import com.example.rod.answer.entity.Answer;
import com.example.rod.question.entity.Question;
import lombok.Getter;


@Getter
public class QuestionResponse {

    private String title;
    private String content;


    public QuestionResponse(Question question) {
        this.title = question.getTitle();
        this.content = question.getContent();
    }

}
