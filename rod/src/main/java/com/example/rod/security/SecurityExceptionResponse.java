package com.example.rod.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SecurityExceptionResponse {

    private int statusCode;
    private String msg;
    public SecurityExceptionResponse(int statusCode, String msg){
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
