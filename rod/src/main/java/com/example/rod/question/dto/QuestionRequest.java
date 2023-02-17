package com.example.rod.question.dto;


import com.example.rod.question.entity.Tag;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionRequest {

    private String title;   // 질문 제목
    private String content; // 질문 내용

    private List<Tag> tagList;  // 태그 리스트

}
