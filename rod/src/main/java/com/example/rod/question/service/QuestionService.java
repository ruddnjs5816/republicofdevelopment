package com.example.rod.question.service;

import com.example.rod.question.dto.*;
import com.example.rod.user.entity.User;

import java.util.List;

public interface QuestionService {

    void createQuestion(QuestionRequest questionRequest /*User user*/);

    List<GetQuestionResponse> getMyQuestions(Long userId/*String userId*/); // By Security

    List<GetQuestionResponse> getQuestions();

    GetQuestionResponse getSpecificQuestion(Long questionId);

    void changeQuestionTitle(Long questionId, PatchQuestionTitleRequest patchQuestionTitleRequest);

    void changeQuestionContent(Long questionId, PatchQuestionContentRequest patchQuestionContentRequest);

    void deleteQuestion(Long questionId);



}
