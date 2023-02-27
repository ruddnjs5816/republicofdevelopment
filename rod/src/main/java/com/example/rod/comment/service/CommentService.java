package com.example.rod.comment.service;

import com.example.rod.comment.dto.*;
import com.example.rod.security.details.UserDetailsImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    CreateCommentResponseDto createComment(Long answerId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails);

    UpdateCommentResponseDto updateComment(Long answerId, Long commentsId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails);

    DeleteCommentResponseDto deleteComment(Long answerId, Long commentsId, UserDetailsImpl userDetails);

    List<CommentResponseDto> getComments(Long answerId, int page, Pageable pageable);

    /*CommentResultDto getListComment(int offset, int limit);*/

}
