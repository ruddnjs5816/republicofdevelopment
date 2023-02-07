package com.example.rod.question.dto;

import lombok.Getter;



@Getter
public class PatchQuestionResponse {
    private String message;

    public PatchQuestionResponse(String message){
        this.message = message;
    }

}