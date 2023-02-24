package com.example.rod.answer.dto;

import com.example.rod.comment.dto.CommentResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Builder
public class AnswerWithCommentsDto {

    private Long answerId;

    private String nickname;
    private String createdAt;
    private String content;
    private boolean isSelected;
    private int likes;
    private List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

    public AnswerWithCommentsDto(Long answerId, String nickname, String createdAt, String content, boolean isSelected, int likes, List<CommentResponseDto> commentResponseDtoList) {
        this.answerId = answerId;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.content = content;
        this.isSelected = isSelected;
        this.likes = likes;
        this.commentResponseDtoList = commentResponseDtoList;
    }

    public static class AnswerWithCommentsDtoBuilder{
        private String createdAt;

        public AnswerWithCommentsDtoBuilder createdAt(LocalDateTime createdAt){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String formattedTime = createdAt.format(formatter);
            this.createdAt = formattedTime;
            return this;
        }
    }



}
