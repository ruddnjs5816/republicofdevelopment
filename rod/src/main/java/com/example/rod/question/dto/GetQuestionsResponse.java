package com.example.rod.question.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class GetQuestionsResponse {

    private int currentPage;

    private List<QuestionResponse> data;

    public GetQuestionsResponse(int currentPage, List<QuestionResponse> data) {
        this.currentPage = currentPage;
        this.data = data;
    }
}
