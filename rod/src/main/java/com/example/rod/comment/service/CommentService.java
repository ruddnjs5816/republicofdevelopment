package com.example.rod.comment.service;

import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.comment.dto.CommentRequestDto;
import com.example.rod.comment.dto.CommentResponseDto;
import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(Long answerId, CommentRequestDto commentRequestDto) {
        answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답변이 존재하지 않습니다.")
        );

        Comment saved = commentRepository.save(new Comment(commentRequestDto.getContent()));

        return new CommentResponseDto(saved.getId(), saved.getContent());
    }

    public CommentResponseDto updateComment(Long answerId, Long commentsId, CommentRequestDto commentRequestDto) {
        answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));

        Comment save = commentRepository.save(comment);
        return new CommentResponseDto(save.getId(), save.getContent());
    }

    public void deleteComment(Long answerId, Long commentsId) {
        answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));

        commentRepository.deleteById(comment.getId());

    }

    public List<CommentResponseDto> getListComment() {
        List<Comment> commentAll = commentRepository.findAll();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentAll) {
            commentResponseDtoList.add(new CommentResponseDto(comment.getId(), comment.getContent()));
        }
        return commentResponseDtoList;
    }


}
