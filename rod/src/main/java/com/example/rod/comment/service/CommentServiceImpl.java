package com.example.rod.comment.service;

import com.example.rod.answer.dto.AnswerResponseDto;
import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.comment.dto.CommentRequestDto;
import com.example.rod.comment.dto.CommentResponseDto;
import com.example.rod.comment.dto.CommentResultDto;
import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl {

    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(Long answerId, CommentRequestDto commentRequestDto) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답변이 존재하지 않습니다.")
        );
        Comment comment = new Comment(commentRequestDto.getContent());
        comment.setFK(answer);
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long answerId, Long commentsId, CommentRequestDto commentRequestDto) {

        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));

        comment.updateContent(commentRequestDto.getContent());
    }

    @Transactional
    public void deleteComment(Long answerId, Long commentsId) {
        answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));

        commentRepository.deleteById(comment.getId());

    }


    @Transactional(readOnly = true)
    public CommentResultDto getListComment(int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<Comment> page = commentRepository.findAll(pageRequest);
        Page<CommentResponseDto> map = page.map(Comment -> new CommentResponseDto(Comment.getId(), Comment.getContent()));
        List<CommentResponseDto> commentAll = map.getContent();
        long totalCount = map.getTotalElements();

        CommentResultDto resultDto = new CommentResultDto(offset,  commentAll);

        return resultDto;
    }
}
