package com.example.rod.question.service;

import com.example.rod.question.dto.*;
import com.example.rod.security.details.UserDetailsImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface QuestionService {

    //void createQuestion(QuestionRequest questionRequest, UserDetailsImpl userDetails);
    CreateQuestionResponseDto createQuestion(QuestionRequest questionRequest, UserDetailsImpl userDetails);

    GetQuestionsResponse getMyQuestions(UserDetailsImpl userDetails, Pageable pageable, int page);

    GetQuestionsResponse getQuestions(Pageable pageable, int page);

    QuestionWithAnswersResponse  getSpecificQuestion(Long questionId, int page, int size);

    SelectAnswerForQuestionResponseDto selectAnswerForQuestion(Long questionId, Long answerId, UserDetailsImpl userDetails);

    ChangeQuestionResponseDto changeQuestion(Long questionId, ChangeQuestionRequest changeQuestionRequest, UserDetailsImpl userDetails);

    //질문 삭제
    DeleteQuestionResponseDto deleteQuestion(Long questionId, UserDetailsImpl userDetails);

    // 답변 삭제
    void deleteAnswer(Long answerId,UserDetailsImpl userDetails);

    //댓글 삭제
    void deleteComment(Long answerId, Long commentsId, UserDetailsImpl userDetails);

    GetQuestionsResponse searchQuestion(Optional<String> title, Optional<String> nickname, Optional<String> hashtagname,
                                        int page, Pageable pageable);

    Object findAllDesc();
}
