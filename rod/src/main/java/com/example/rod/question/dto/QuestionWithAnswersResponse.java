package com.example.rod.question.dto;

import com.example.rod.answer.dto.AnswerWithCommentsDto;
import com.example.rod.question.entity.HashTag;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionWithAnswersResponse {

    private String title;
    private String content;

    private HashTagDto tagList;

    private double difficulty;
    private List<AnswerWithCommentsDto> answerWithComments;



    public QuestionWithAnswersResponse(String title, String content, HashTagDto tagList, double difficulty, List<AnswerWithCommentsDto> answerWithComments){
        this.title = title;
        this.content = content;
        this.tagList = tagList;
        this.difficulty = difficulty;
        this.answerWithComments = answerWithComments;
    }

}