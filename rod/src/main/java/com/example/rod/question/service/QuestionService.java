package com.example.rod.question.service;

import com.example.rod.question.dto.*;
import com.example.rod.security.details.UserDetailsImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionService {

//    void createQuestion(QuestionRequest questionRequest, UserDetailsImpl userDetails);
    CreateQuestionResponseDto createQuestion(QuestionRequest questionRequest, UserDetailsImpl userDetails);

    GetQuestionsResponse getMyQuestions(UserDetailsImpl userDetails, Pageable pageable, int page);

    GetQuestionsResponse getQuestions(Pageable pageable, int page);

    QuestionWithAnswersResponse  getSpecificQuestion(Long questionId);

    SelectAnswerForQuestionResponseDto selectAnswerForQuestion(Long questionId, Long answerId, UserDetailsImpl userDetails);

    ChangeQuestionResponseDto changeQuestion(Long questionId, ChangeQuestionRequest changeQuestionRequest, UserDetailsImpl userDetails);

    DeleteQuestionResponseDto deleteQuestion(Long questionId, UserDetailsImpl userDetails);

    GetQuestionsResponse searchQuestion(Optional<String> title, Optional<String> nickname, Optional<String> hashtagname,
                                               int page, Pageable pageable);


//    void uploadImage(MultipartFile image) throws GetException;

}
