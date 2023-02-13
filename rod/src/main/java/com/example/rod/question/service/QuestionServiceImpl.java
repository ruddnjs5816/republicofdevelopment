package com.example.rod.question.service;

import com.example.rod.question.dto.*;
import com.example.rod.question.entity.Question;
import com.example.rod.question.repository.QuestionRepository;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createQuestion(QuestionRequest questionRequest /*User user*/){
        Question question = new Question(questionRequest);
        questionRepository.save(question);
    }

    @Override
    public GetQuestionsResponse getMyQuestions(Long userId, Pageable pageable, int page) {

        User user = userRepository.findById(userId).orElseThrow
                (()-> new IllegalArgumentException("해당 아이디의 유저가 없습니다."));

        Page<Question> questionList = questionRepository.findAllByUser(user, pageable.withPage(page-1));

        List<QuestionResponse> questionResponseList = new ArrayList<>();

        for (Question question : questionList) {
            questionResponseList.add(new QuestionResponse(question));
        }

        return new GetQuestionsResponse(page, questionResponseList);
    }

    @Override
    public GetQuestionsResponse getQuestions(Pageable pageable, int page){

        Page<Question> questionList = questionRepository.findAll(pageable.withPage(page-1));


        List<QuestionResponse> questionResponseList = new ArrayList<>();


        for (Question question : questionList) {
            questionResponseList.add(new QuestionResponse(question));
        }

        return new GetQuestionsResponse(page, questionResponseList);
    }


    @Override
    public QuestionWithAnswersResponse getSpecificQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow
                (() -> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));
        return new QuestionWithAnswersResponse(question);
    }

    @Override
    @Transactional
    public void changeQuestionTitle(Long questionId, PatchQuestionTitleRequest patchQuestionTitleRequest) {
        Question question = questionRepository.findById(questionId).orElseThrow
                (()-> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));
        question.editTitle(patchQuestionTitleRequest.getTitle());
    }


    @Override
    @Transactional
    public void changeQuestionContent(Long questionId, PatchQuestionContentRequest patchQuestionContentRequest){
        Question question = questionRepository.findById(questionId).orElseThrow
                (()-> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));
        question.editContent(patchQuestionContentRequest.getContent());
    }


    @Override
    @Transactional
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }


}
