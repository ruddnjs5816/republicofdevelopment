package com.example.rod.answer.dto;

import com.example.rod.comment.dto.commentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class answerResponseDto {

    private Long id;
    private String content;

    private int answerLike;

    private List<commentResponseDto> commentResponseDtoList;

    public answerResponseDto(Long id, String content, int answerLike) {
        this.id = id;
        this.content = content;
        this.answerLike = answerLike;
    }

    public answerResponseDto(String content) {
        this.content = content;
    }
}
