package com.example.rod.question.dto;


import lombok.Getter;

@Getter
public class QuestionRequest {

    private String title;   // 질문 제목
    private String content; // 질문 내용

    private String hashtagStrs;  // 태그 내용들

}
