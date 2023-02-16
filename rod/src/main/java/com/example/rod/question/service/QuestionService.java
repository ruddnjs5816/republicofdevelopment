package com.example.rod.question.service;

import com.example.rod.question.dto.*;
import com.example.rod.security.details.UserDetailsImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionService {

//    void createQuestion(QuestionRequest questionRequest, UserDetailsImpl userDetails);
    void createQuestion(QuestionRequest questionRequest, UserDetailsImpl userDetails);

    GetQuestionsResponse getMyQuestions(UserDetailsImpl userDetails, Pageable pageable, int page);

    GetQuestionsResponse getQuestions(Pageable pageable, int page);

    QuestionWithAnswersResponse  getSpecificQuestion(Long questionId);

    void changeQuestionTitle(Long questionId, PatchQuestionTitleRequest patchQuestionTitleRequest, UserDetailsImpl userDetails);

    void changeQuestionContent(Long questionId, PatchQuestionContentRequest patchQuestionContentRequest, UserDetailsImpl userDetails);

    void deleteQuestion(Long questionId, UserDetailsImpl userDetails);



}
