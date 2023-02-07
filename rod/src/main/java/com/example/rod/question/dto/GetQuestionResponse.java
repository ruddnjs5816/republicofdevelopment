package com.example.rod.question.dto;

import com.example.rod.question.entity.Question;
import lombok.Getter;

@Getter
public class GetQuestionResponse {

    private String title;
    private String content;

    public GetQuestionResponse(Question question) {
        this.title = question.getTitle();
        this.content = question.getContent();
    }

}
