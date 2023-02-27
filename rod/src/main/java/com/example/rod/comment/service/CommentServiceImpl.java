package com.example.rod.comment.service;

import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.comment.dto.*;
import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.repository.CommentRepository;
import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateCommentResponseDto createComment(Long answerId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답변이 존재하지 않습니다.")
        );

        User writer = userDetails.getUser();


        Comment comment = Comment.builder().
                user(writer).
                answer(answer).
                content(commentRequestDto.getContent()).build();

        commentRepository.save(comment);

        return new CreateCommentResponseDto(comment.getId());
    }

    @Transactional
    public UpdateCommentResponseDto updateComment(Long answerId, Long commentsId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {

        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답변이 존재하지 않습니다.")
        );

        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));

        if(!comment.isOwnedBy(answer)){
            throw new IllegalArgumentException("잘못된 요청입니다. 해당 질문에 이 댓글이 존재하지 않습니다.");
        }


        User writer = userDetails.getUser();

        if(comment.isOwnedBy(writer)){
            comment.updateContent(commentRequestDto.getContent());
        } else {
            throw new IllegalArgumentException("수정 권한이 없는 유저입니다.");
        }

        return new UpdateCommentResponseDto(commentsId);
    }

    @Transactional
    @Override
    public DeleteCommentResponseDto deleteComment(Long answerId, Long commentId, UserDetailsImpl userDetails) {


        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답변이 존재하지 않습니다.")
        );


        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));

        if(!comment.isOwnedBy(answer)){
            throw new IllegalArgumentException("잘못된 요청입니다. 해당 질문에 이 댓글이 존재하지 않습니다.");
        }

        User user = userDetails.getUser();

        if(comment.isOwnedBy(user)){
            commentRepository.deleteById(commentId);
        } else {
            throw new IllegalArgumentException("삭제 권한이 없는 유저입니다.");
        }

        return new DeleteCommentResponseDto(commentId);
    }

    @Override
    @Transactional
    public List<CommentResponseDto> getComments(Long answerId, int page, Pageable pageable) {

        Page<Comment> comments = commentRepository.findAllByAnswerId(answerId, pageable.withPage(page-1));

        List<CommentResponseDto> commentResponseDtoList= comments.stream()
                .map(comment -> CommentResponseDto.builder()
                        .commentId(comment.getId())
                        .nickName(comment.getUser().getNickname())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return commentResponseDtoList;
    }

/*
    @Transactional(readOnly = true)
    public CommentResultDto getListComment(int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<Comment> page = commentRepository.findAll(pageRequest);
        Page<CommentResponseDto> map = page.map(Comment -> new CommentResponseDto(Comment.getId(), Comment.getContent()));
        List<CommentResponseDto> commentAll = map.getContent();
        long totalCount = map.getTotalElements();

        CommentResultDto resultDto = new CommentResultDto(offset,  commentAll);

        return resultDto;
    }*/
}
