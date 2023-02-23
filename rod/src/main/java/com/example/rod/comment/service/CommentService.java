package com.example.rod.comment.service;

import com.example.rod.comment.dto.CommentRequestDto;
import com.example.rod.comment.dto.CommentResponseDto;
import com.example.rod.comment.dto.CommentResultDto;
import com.example.rod.security.details.UserDetailsImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    void createComment(Long answerId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails);

    void updateComment(Long answerId, Long commentsId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails);

    void deleteComment(Long answerId, Long commentsId, UserDetailsImpl userDetails);

    List<CommentResponseDto> getComments(Long answerId, int page, Pageable pageable);

    /*CommentResultDto getListComment(int offset, int limit);*/

}
