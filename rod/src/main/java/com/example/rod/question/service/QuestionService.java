package com.example.rod.question.service;

import com.example.rod.question.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionService {

    void createQuestion(QuestionRequest questionRequest, Long userId);

    GetQuestionsResponse getMyQuestions(Long userId, Pageable pageable, int page/*String userId*/); // By Security

    GetQuestionsResponse getQuestions(Pageable pageable, int page);

    QuestionWithAnswersResponse  getSpecificQuestion(Long questionId);

    void changeQuestionTitle(Long questionId, PatchQuestionTitleRequest patchQuestionTitleRequest);

    void changeQuestionContent(Long questionId, PatchQuestionContentRequest patchQuestionContentRequest);

    void deleteQuestion(Long questionId);



}
