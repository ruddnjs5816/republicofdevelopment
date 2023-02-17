package com.example.rod.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResultDto {


    private int currentPage;

    private Long answerCount;

    private List<AnswerResponseDto> data;

    public AnswerResultDto(int page, List<AnswerResponseDto> resultList) {
        this.currentPage = page;
        this.data = resultList;
    }
}
