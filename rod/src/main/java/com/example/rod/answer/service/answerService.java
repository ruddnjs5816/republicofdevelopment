package com.example.rod.answer.service;

import com.example.rod.answer.dto.answerRequestDto;
import com.example.rod.answer.dto.answerResponseDto;
import com.example.rod.answer.entity.AnswerEntity;
import com.example.rod.answer.repository.answerRepository;
import com.example.rod.comment.dto.commentResponseDto;
import com.example.rod.comment.entity.commentEntity;
import com.example.rod.comment.repository.commentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class answerService {

    private final answerRepository answerrepository;
    private final commentRepository commentrepository;

    public answerResponseDto createAnswer(answerRequestDto answerRequestDto) {
        AnswerEntity answerEntity1 = new AnswerEntity(answerRequestDto.getContent());
        AnswerEntity saved = answerrepository.save(answerEntity1);
        return new answerResponseDto(saved.getAnswerId(), saved.getContent(), answerEntity1.getLikes());
    }

    public answerResponseDto updateAnswer(Long answerId, answerRequestDto answerRequestDto) {
        AnswerEntity answerEntitySaved = answerrepository.findById(answerId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        answerEntitySaved.update(answerRequestDto.getContent());
        answerrepository.save(answerEntitySaved);
        return new answerResponseDto(answerEntitySaved.getContent());
    }

    public String deleteAnswer(Long answerId) {
        answerrepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 답변입니다."));
        answerrepository.deleteById(answerId);
        return "삭제 완료";
    }


    public answerResponseDto getAnswer(Long answerId) {
        AnswerEntity answerEntity = answerrepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 답변입니다."));
        return new answerResponseDto(answerEntity.getAnswerId(), answerEntity.getContent(), answerEntity.getLikes());
    }

    public List<answerResponseDto> getListAnswer() {
        List<answerResponseDto> answerResponseDtoList = new ArrayList<>();

        List<AnswerEntity> allAnswer = answerrepository.findAll();
        for (AnswerEntity answerEntity : allAnswer) {
            answerResponseDtoList.add(new answerResponseDto(answerEntity.getAnswerId(), answerEntity.getContent(), answerEntity.getLikes()));
        }

        List<commentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<commentEntity> allComment = commentrepository.findAll();


        for (commentEntity commentEntity : allComment) {
            commentrepository.findById(commentEntity)
        }



        return answerResponseDtoList;
    }



}
