package com.example.rod.question.service;

import com.example.rod.question.dto.*;

import java.util.List;

public interface QuestionService {

    CreateQuestionResponse createQuestion(QuestionRequest questionRequest /*User user*/);

    List<GetQuestionResponse> getQuestions(Long userId /*String userId*/); // By Security

    PatchQuestionResponse changeQuestion(Long questionId, PatchQuestionRequest patchQuestionRequest);



}
