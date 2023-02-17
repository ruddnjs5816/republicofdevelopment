package com.example.rod.answer.dto;

import com.example.rod.answer.entity.Answer;
import com.example.rod.comment.dto.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class AnswerResponseDto {

    private Long answerId;
    private String content;
    private int likes;
    private List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

}