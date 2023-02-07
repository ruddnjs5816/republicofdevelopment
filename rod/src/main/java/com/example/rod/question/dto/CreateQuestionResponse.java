package com.example.rod.question.dto;

import lombok.Getter;

@Getter
public class CreateQuestionResponse {
    private String message;

    public CreateQuestionResponse(String message){
        this.message = message;
    }

}
