package com.example.rod.comment.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class commentResponseDto {

    private Long id;
    private String content;

    public commentResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }


}
