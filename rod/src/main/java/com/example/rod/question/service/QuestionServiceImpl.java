package com.example.rod.question.service;

import com.example.rod.answer.dto.AnswerResponseDto;
import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.comment.dto.CommentResponseDto;
import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.repository.CommentRepository;
import com.example.rod.question.dto.*;
import com.example.rod.question.entity.Question;
import com.example.rod.question.repository.QuestionRepository;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createQuestion(QuestionRequest questionRequest, Long userId){
        Question question = new Question(questionRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("찾는 유저가 없습니다.")
        );
        question.setFK(user);
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

        List<AnswerResponseDto> answerWithComments = new ArrayList<>();


        // 1. AnswerList 페이징 처리해서 뽑아온다.
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "createdAt"));

        Page<Answer> answerList = answerRepository.findByQuestion(question, pageRequest);

        // 2.Answer -> AnswerResponseDto로 변환.

        for( Answer answer : answerList ){
            List<CommentResponseDto> comments = new ArrayList<>();
            for(Comment comment : answer.getComments()){
                CommentResponseDto commentResponseDto = new CommentResponseDto(comment.getId(), comment.getContent());
                comments.add(commentResponseDto);
            }
            AnswerResponseDto answerResponseDto = new AnswerResponseDto(answer.getId(), answer.getContent(), answer.getLikes(), comments);
            answerWithComments.add(answerResponseDto);
        }

        QuestionWithAnswersResponse questionWithAnswersResponse = new QuestionWithAnswersResponse(question.getTitle(), question.getContent(), answerWithComments);

        return questionWithAnswersResponse;
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
