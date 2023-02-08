
package com.example.rod.answer.service;

import com.example.rod.answer.dto.AnswerRequestDto;
import com.example.rod.answer.dto.AnswerResponseDto;
import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.comment.dto.CommentResponseDto;
import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.repository.commentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final commentRepository commentRepository;

    public AnswerResponseDto createAnswer(AnswerRequestDto answerRequestDto) {
        Answer answerEntity1 = new Answer(answerRequestDto.getContent());
        Answer saved = answerRepository.save(answerEntity1);
        return new AnswerResponseDto(saved.getAnswerId(), saved.getContent(), answerEntity1.getLikes());
    }

    public AnswerResponseDto updateAnswer(Long answerId, AnswerRequestDto answerRequestDto) {
        Answer answerEntitySaved = answerRepository.findById(answerId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        answerEntitySaved.update(answerRequestDto.getContent());
        answerRepository.save(answerEntitySaved);
        return new AnswerResponseDto(answerEntitySaved.getContent());
    }

    public String deleteAnswer(Long answerId) {
        answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 답변입니다."));
        answerRepository.deleteById(answerId);
        return "삭제 완료";
    }


    public AnswerResponseDto getAnswer(Long answerId) {
        Answer answerEntity = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 답변입니다."));
        return new AnswerResponseDto(answerEntity.getAnswerId(), answerEntity.getContent(), answerEntity.getLikes());
    }

    public List<AnswerResponseDto> getListAnswer() {
        List<AnswerResponseDto> answerResponseDtoList = new ArrayList<>();

        List<Answer> allAnswer = answerRepository.findAll();
        for (Answer answerEntity : allAnswer) {
            answerResponseDtoList.add(new AnswerResponseDto(answerEntity.getAnswerId(), answerEntity.getContent(), answerEntity.getLikes()));
        }

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<Comment> allComment = commentRepository.findAll();


        for (Comment commentEntity : allComment) {
            commentRepository.findById(commentEntity.getId());
        }



        return answerResponseDtoList;
    }



}

