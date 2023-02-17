package com.example.rod.question.service;

import com.example.rod.question.dto.*;
import com.example.rod.security.details.UserDetailsImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuestionService {

//    void createQuestion(QuestionRequest questionRequest, UserDetailsImpl userDetails);
    void createQuestion(QuestionRequest questionRequest, UserDetailsImpl userDetails);

    GetQuestionsResponse getMyQuestions(Long userId, Pageable pageable, int page/*String userId*/); // By Security

    GetQuestionsResponse getQuestions(Pageable pageable, int page);

    QuestionWithAnswersResponse  getSpecificQuestion(Long questionId);

    void changeQuestionTitle(Long questionId, PatchQuestionTitleRequest patchQuestionTitleRequest);

    void changeQuestionContent(Long questionId, PatchQuestionContentRequest patchQuestionContentRequest);

    void deleteQuestion(Long questionId);


    void uploadImage(MultipartFile image);
}
