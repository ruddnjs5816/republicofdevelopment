package com.example.rod.question.service;

import com.example.rod.answer.dto.AnswerWithCommentsDto;
import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.comment.dto.CommentResponseDto;
import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.repository.CommentRepository;
import com.example.rod.question.dto.*;
import com.example.rod.question.entity.Question;
import com.example.rod.question.repository.QuestionRepository;
import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public void createQuestion(QuestionRequest questionRequest, UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Question question = Question.builder()
                .title(questionRequest.getTitle())
                .content(questionRequest.getContent())
                .user(user)
                .build();
        questionRepository.save(question);
    }

    @Override
    @Transactional
    public GetQuestionsResponse getMyQuestions(UserDetailsImpl userDetails,Pageable pageable, int page) {

        User user = userDetails.getUser();

        Page<Question> questionList = questionRepository.findAllByUser(user, pageable.withPage(page-1));

        List<QuestionResponse> questionResponseList = new ArrayList<>();

        for (Question question : questionList) {
            questionResponseList.add(new QuestionResponse(question.getQuestionId(), question.getTitle(), question.getContent()));
        }
        return new GetQuestionsResponse(page, questionResponseList);
    }

    @Override
    @Transactional
    public GetQuestionsResponse getQuestions(Pageable pageable, int page){

        Page<Question> questionList = questionRepository.findAll(pageable.withPage(page-1));


        List<QuestionResponse> questionResponseList = new ArrayList<>();


        for (Question question : questionList) {
            questionResponseList.add(new QuestionResponse(question.getQuestionId(), question.getTitle(), question.getContent()));
        }

        return new GetQuestionsResponse(page, questionResponseList);
    }


    @Override
    @Transactional
    public QuestionWithAnswersResponse getSpecificQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow
                (() -> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));

        List<AnswerWithCommentsDto> answerWithComments = new ArrayList<>();


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
            AnswerWithCommentsDto answerWithCommentsDto = new AnswerWithCommentsDto(answer.getId(), answer.getContent(), answer.getLikes(), comments);
            answerWithComments.add(answerWithCommentsDto);
        }

        QuestionWithAnswersResponse questionWithAnswersResponse = new QuestionWithAnswersResponse(question.getTitle(), question.getContent(), answerWithComments);

        return questionWithAnswersResponse;
    }


    @Override
    @Transactional
    public void selectAnswerForQuestion(Long questionId, Long answerId, UserDetailsImpl userDetails) {

        User questioner = userDetails.getUser();

        Question question = questionRepository.findById(questionId).orElseThrow
                (() -> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));

        if(question.isOwnedBy(questioner)){

            Answer answer = answerRepository.findById(answerId).orElseThrow(
                    () -> new IllegalArgumentException("해당하는 아이디의 답변이 없습니다.")
            );

            if(question.contains(answer)) {
                question.close();
                answer.select();

                User answerer = answer.getUser();

                increaseUserRating(answerer);
                rewardPointToAnswererSelected(answerer);
            } else {
                throw new IllegalArgumentException("질문에 포함되지 않은 답변은 채택할 수 없습니다.");
            }


        } else {
            throw new IllegalArgumentException("채택은 질문자만 할 수 있습니다.");
        }
    }

    @Override
    @Transactional
    public void changeQuestionTitle(Long questionId, PatchQuestionTitleRequest patchQuestionTitleRequest, UserDetailsImpl userDetails) {
        Question question = questionRepository.findById(questionId).orElseThrow
                (()-> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));

        User user = userDetails.getUser();

        if(question.isOwnedBy(user)){
            question.editTitle(patchQuestionTitleRequest.getTitle());
        } else {
            throw new IllegalArgumentException("수정 권한이 없는 유저입니다.");
        }
    }


    @Override
    @Transactional
    public void changeQuestionContent(Long questionId, PatchQuestionContentRequest patchQuestionContentRequest, UserDetailsImpl userDetails){
        Question question = questionRepository.findById(questionId).orElseThrow
                (()-> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));

        User user = userDetails.getUser();

        if(question.isOwnedBy(user)){
            question.editContent(patchQuestionContentRequest.getContent());
        } else {
            throw new IllegalArgumentException("수정 권한이 없는 유저입니다.");
        }


    }


    @Override
    @Transactional
    public void deleteQuestion(Long questionId, UserDetailsImpl userDetails) {

        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 질문이 없습니다.")
        );

        User user = userDetails.getUser();

        if(question.isOwnedBy(user)){
            questionRepository.deleteById(questionId);
        } else {
            throw new IllegalArgumentException("삭제 권한이 없는 유저입니다.");
        }
    }


    public void increaseUserRating(User user){
        // 레이팅 상승 로직 ( default : 채택 시 10점 상승 )
        user.increaseRating(10);
    }

    public void rewardPointToAnswererSelected(User user){
        // 포인트 지급 로직 ( 답변 채택 시 )
        user.increasePoint(100);
    }


}
