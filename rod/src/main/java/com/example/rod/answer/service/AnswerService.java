package com.example.rod.answer.service;

import com.example.rod.answer.dto.AnswerRequestDto;
import com.example.rod.answer.dto.AnswerWithCommentsDto;
import com.example.rod.answer.dto.AnswerResponseDto;
import com.example.rod.security.details.UserDetailsImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnswerService {

    void createAnswer(Long questionId, AnswerRequestDto answerRequestDto, UserDetailsImpl userDetails);

    void updateAnswer(Long answerId, AnswerRequestDto answerRequestDto, UserDetailsImpl userDetails);

    void deleteAnswer(Long answerId, UserDetailsImpl userDetails);

    List<AnswerResponseDto> getMyAnswers(Pageable pageable, int page, UserDetailsImpl userDetails);

    AnswerWithCommentsDto getAnswer(Long answerId, UserDetailsImpl userDetails);



}
