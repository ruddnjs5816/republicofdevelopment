package com.example.rod.question.dto;

import com.example.rod.answer.entity.Answer;
import com.example.rod.question.entity.Question;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionWithAnswersResponse {

    private String title;
    private String content;
    private List<Answer> answers;

    public QuestionWithAnswersResponse(Question question) {
        this.title = question.getTitle();
        this.content = question.getContent();
        this.answers = question.getAnswers();
    }

}
