package com.example.rod.answer.service;

import com.example.rod.answer.dto.*;
import com.example.rod.security.details.UserDetailsImpl;
import org.hibernate.sql.Delete;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnswerService {

    CreateAnswerResponseDto createAnswer(Long questionId, AnswerRequestDto answerRequestDto, UserDetailsImpl userDetails);

    UpdateAnswerResponseDto updateAnswer(Long answerId, AnswerRequestDto answerRequestDto, UserDetailsImpl userDetails);

    DeleteAnswerResponseDto deleteAnswer(Long answerId, UserDetailsImpl userDetails);

    List<AnswerResponseDto> getMyAnswers(Pageable pageable, int page, UserDetailsImpl userDetails);

    AnswerWithCommentsDto getAnswer(Long answerId, UserDetailsImpl userDetails);



}
