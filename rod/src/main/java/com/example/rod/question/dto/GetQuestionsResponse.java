package com.example.rod.question.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class GetQuestionsResponse {

    private Integer currentPage;

    private Long totalQuestionCount;

    private List<QuestionResponse> data;

    public GetQuestionsResponse(Integer currentPage, Long totalQuestionCount, List<QuestionResponse> data) {
        this.currentPage = currentPage;
        this.totalQuestionCount = totalQuestionCount;
        this.data = data;
    }
}
