package com.example.rod.question.service;

import com.example.rod.question.dto.*;
import com.example.rod.question.entity.Question;
import com.example.rod.question.repository.QuestionRepository;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<GetQuestionResponse> getMyQuestions(Long userId) {

        User user = userRepository.findById(userId).orElseThrow
                (()-> new IllegalArgumentException("해당 아이디의 유저가 없습니다."));

        List<Question> questionList = questionRepository.findByUser(user);

        // Question 객체 리스트를 DTO 리스트로 변환
        List<GetQuestionResponse> getMyQuestionResponseList = questionList.stream()
                .map(GetQuestionResponse::new)
                .collect(Collectors.toList());

        return getMyQuestionResponseList;
    }

    @Override
    public List<GetQuestionResponse> getQuestions(){
        List<Question> questionList = questionRepository.findAll();

        List<GetQuestionResponse> getQuestionResponseList = questionList.stream()
                .map(GetQuestionResponse::new)
                .collect(Collectors.toList());

        return getQuestionResponseList;
    }


    @Override
    public GetQuestionResponse getSpecificQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow
                (() -> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));
        return new GetQuestionResponse(question);
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
