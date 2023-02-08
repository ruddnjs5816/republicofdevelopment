/*
package com.example.rod.comment.service;

import com.example.rod.answer.repository.answerRepository;
import com.example.rod.comment.dto.commentRequestDto;
import com.example.rod.comment.dto.commentResponseDto;
import com.example.rod.comment.entity.commentEntity;
import com.example.rod.comment.repository.commentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class commentService {

    private final answerRepository answerRepository;
    private final commentRepository commentRepository;

    public commentResponseDto createComment(Long answerId, commentRequestDto commentRequestDto) {
        answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답변이 존재하지 않습니다.")
        );

        commentEntity saved = commentRepository.save(new commentEntity(commentRequestDto.getContent()));

        return new commentResponseDto(saved.getId(), saved.getContent());
    }

    public commentResponseDto updateComment(Long answerId, Long commentsId, commentRequestDto commentRequestDto) {
        answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));

        commentEntity comment = commentRepository.findById(commentsId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));

        commentEntity save = commentRepository.save(comment);
        return new commentResponseDto(save.getId(), save.getContent());
    }

    public void deleteComment(Long answerId, Long commentsId) {
        answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));

        commentEntity comment = commentRepository.findById(commentsId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));

        commentRepository.deleteById(comment.getId());

    }

    public List<commentResponseDto> getListComment() {
        List<commentEntity> commentAll = commentRepository.findAll();
        List<commentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (commentEntity comment : commentAll) {
            commentResponseDtoList.add(new commentResponseDto(comment.getId(), comment.getContent()));
        }
        return commentResponseDtoList;
    }


}
*/
