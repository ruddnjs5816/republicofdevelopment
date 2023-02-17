package com.example.rod.comment.service;

import com.example.rod.comment.dto.CommentRequestDto;
import com.example.rod.comment.dto.CommentResultDto;
import com.example.rod.security.details.UserDetailsImpl;

public interface CommentService {

    void createComment(Long answerId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails);

    void updateComment(Long answerId, Long commentsId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails);

    void deleteComment(Long answerId, Long commentsId, UserDetailsImpl userDetails);

    /*CommentResultDto getListComment(int offset, int limit);*/

}
