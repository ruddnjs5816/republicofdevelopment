package com.example.rod.comment.dto;

import com.example.rod.answer.dto.AnswerResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommentResultDto<T> {

    private int currentPage;

//    private List data;
    private T data;


}
