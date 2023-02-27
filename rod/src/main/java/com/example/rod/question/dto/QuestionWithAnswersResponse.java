package com.example.rod.question.dto;

import com.example.rod.answer.dto.AnswerWithCommentsDto;
import com.example.rod.question.entity.HashTag;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
public class QuestionWithAnswersResponse {

    private String title;
    private String content;

    private String nickname;
    private String createdAt;

    private HashTagDto tagList;

    private Long totalAnswerCount;

    private double difficulty;

    private List<AnswerWithCommentsDto> answerWithComments;


    public QuestionWithAnswersResponse(String title, String content, String nickname, String createdAt, HashTagDto tagList, Long totalAnswerCount, double difficulty, List<AnswerWithCommentsDto> answerWithComments) {
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.tagList = tagList;
        this.totalAnswerCount = totalAnswerCount;
        this.difficulty = difficulty;
        this.answerWithComments = answerWithComments;
    }

    public static class QuestionWithAnswersResponseBuilder {
        private String createdAt;

        public QuestionWithAnswersResponse.QuestionWithAnswersResponseBuilder createdAt(LocalDateTime createdAt){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String formattedTime = createdAt.format(formatter);
            this.createdAt = formattedTime;
            return this;
        }
    }

}