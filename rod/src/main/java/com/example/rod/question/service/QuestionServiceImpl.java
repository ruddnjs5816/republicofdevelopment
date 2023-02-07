package com.example.rod.question.service;

import com.example.rod.question.dto.*;
import com.example.rod.question.entity.Question;
import com.example.rod.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public CreateQuestionResponse createQuestion(QuestionRequest questionRequest /*User user*/){

        Question question = new Question(questionRequest);

        questionRepository.save(question);

        return new CreateQuestionResponse("질문 생성 완료!");
    }

    @Override
    public List<GetQuestionResponse> getQuestions(Long userId) {
        List<Question> questionList = questionRepository.findAll();

        // Question 객체 리스트를 DTO 리스트로 변환
        List<GetQuestionResponse> getQuestionResponseList = questionList.stream()
                .map(GetQuestionResponse::new)
                .collect(Collectors.toList());

        return getQuestionResponseList;
    }

    @Override
    public PatchQuestionResponse changeQuestion(Long questionId, PatchQuestionRequest patchQuestionRequest) {
        Question question = questionRepository.findById(questionId).orElseThrow
                (()-> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));

        question.edit(patchQuestionRequest.getTitle(), patchQuestionRequest.getContent());

        return new PatchQuestionResponse("수정 완료!");
    }



}
