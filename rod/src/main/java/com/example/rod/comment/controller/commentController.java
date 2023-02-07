package com.example.rod.comment.controller;

import com.example.rod.comment.dto.commentRequestDto;
import com.example.rod.comment.dto.commentResponseDto;
import com.example.rod.comment.service.commentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class commentController {

    private final commentService commentService;

    // 댓글 작성
    @PostMapping("/answers/{answerId}/comments")
    public commentResponseDto createComment
                (@PathVariable Long answerId, @RequestBody commentRequestDto commentRequestDto) {
        return commentService.createComment(answerId, commentRequestDto);
    }

    // 댓글 수정
    @PutMapping("/answers/{answerId}/comments/{commentsId}")
    public commentResponseDto updateComment
              (@PathVariable Long answerId, @PathVariable Long commentsId, commentRequestDto commentRequestDto) {
        return commentService.updateComment(answerId, commentsId, commentRequestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/answers/{answerId}/comments/{commentsId}")
    public void deleteComment(@PathVariable Long answerId, @PathVariable Long commentsId) {
        commentService.deleteComment(answerId, commentsId);
    }

    // 내 댓글 리스트조회
    @GetMapping("/answers")
    public List<commentResponseDto> getListComment() {
        return commentService.getListComment();
    }

}
