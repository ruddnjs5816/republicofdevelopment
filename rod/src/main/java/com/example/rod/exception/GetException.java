package com.example.rod.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GetException {
    private HttpStatus statusCode;
    private String errorMessage;

    public GetException(StatusExceptionCode statusExceptionCode){
        this.statusCode = statusExceptionCode.getStatusCode();
        this.errorMessage = statusExceptionCode.getErrorMessage();
    }
}
