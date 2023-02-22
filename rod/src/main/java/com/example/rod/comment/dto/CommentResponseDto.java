package com.example.rod.comment.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private String content;

    public CommentResponseDto(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
