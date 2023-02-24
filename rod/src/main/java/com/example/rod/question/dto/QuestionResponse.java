package com.example.rod.question.dto;

import com.example.rod.answer.entity.Answer;
import com.example.rod.comment.dto.CommentResponseDto;
import com.example.rod.question.entity.Question;
import lombok.Builder;
import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
public class QuestionResponse {

    private Long questionId;

    private String title;

    // 질문 작성자 이름
    private String nickName;

    // 만든 날짜
    private String createdAt;

    // 이 Question에 달린 답변의 수
    private Integer answerCount;


    @Builder
    public QuestionResponse(Long questionId, String title, String nickName, String createdAt, Integer answerCount) {
        this.questionId = questionId;
        this.title = title;
        this.nickName = nickName;
        this.createdAt = createdAt;
        this.answerCount = answerCount;
    }


    public static class QuestionResponseBuilder{
        private String createdAt;

        public QuestionResponse.QuestionResponseBuilder createdAt(LocalDateTime createdAt){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String formattedTime = createdAt.format(formatter);
            this.createdAt = formattedTime;
            return this;
        }
    }

}
