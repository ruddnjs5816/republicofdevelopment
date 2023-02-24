package com.example.rod.comment.dto;


import com.example.rod.answer.dto.AnswerWithCommentsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Builder
public class CommentResponseDto {

    private Long commentId;
    private String nickName;
    private String content;
    private String createdAt;

    public CommentResponseDto(Long commentId, String nickName, String content, String createdAt) {
        this.commentId = commentId;
        this.nickName = nickName;
        this.content = content;
        this.createdAt = createdAt;
    }



    public static class CommentResponseDtoBuilder{
        private String createdAt;

        public CommentResponseDto.CommentResponseDtoBuilder createdAt(LocalDateTime createdAt){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String formattedTime = createdAt.format(formatter);
            this.createdAt = formattedTime;
            return this;
        }
    }

}
