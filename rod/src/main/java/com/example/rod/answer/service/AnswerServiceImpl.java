package com.example.rod.answer.service;

import com.example.rod.answer.dto.AnswerRequestDto;
import com.example.rod.answer.dto.AnswerWithCommentsDto;
import com.example.rod.answer.dto.AnswerResponseDto;
import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.comment.dto.CommentResponseDto;
import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.repository.CommentRepository;
import com.example.rod.question.entity.Question;
import com.example.rod.question.repository.QuestionRepository;
import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {


    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void createAnswer(Long questionId, AnswerRequestDto answerRequestDto, UserDetailsImpl userDetails) {

        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 질문이 존재하지 않습니다.")
        );

        User answerer = userDetails.getUser();

        if(question.isOwnedBy(answerer)){
            throw new IllegalArgumentException("자신의 질문에는 답변할 수 없습니다.");
        }

        question.calculateDifficulty(answerRequestDto.getDifficulty());

        User answererEntity = userRepository.findById(answerer.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        answererEntity.increasePoint(10); // 답변 작성 시 10포인트 지급


        Answer answer = Answer.builder()
                        .content(answerRequestDto.getContent())
                        .likes(0)
                        .question(question)
                        .user(userDetails.getUser())
                        .build();

        answerRepository.save(answer);
    }


    @Transactional
    @Override
    public void updateAnswer(Long answerId, AnswerRequestDto answerRequestDto, UserDetailsImpl userDetails) {

        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new IllegalArgumentException("답변이 존재하지 않습니다."));

        User user = userDetails.getUser();

        if(answer.isOwnedBy(user)){
            answer.updateContent(answerRequestDto.getContent());
        } else {
            throw new IllegalArgumentException("자신이 답한 질문이 아니면 수정할 수 업습니다.");
        }
    }

    @Transactional
    @Override
    public void deleteAnswer(Long answerId, UserDetailsImpl userDetails) {

        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("답변이 존재하지 않습니다.")
        );

        User user = userDetails.getUser();

        if(answer.isOwnedBy(user)){
            answerRepository.deleteById(answerId);
        } else {
            throw new IllegalArgumentException("자신이 답한 질문이 아니면 삭제할 수 업습니다.");
        }
    }

    // '내 질문 리스트 보기' 호출 시, 유저 아이디, 유저가 답변한 내용, 좋아요 개수 들만 반환.
    @Transactional(readOnly = true)
    @Override
    public List<AnswerResponseDto> getMyAnswers(Pageable pageable, int page, UserDetailsImpl userDetails) {

        User user = userDetails.getUser();

        Page<Answer> answers = answerRepository.findByUser(user, pageable.withPage(page - 1));

        List<AnswerResponseDto> answerReseponseDtoList = answers.stream()
                                                                .map(AnswerResponseDto::new)
                                                                .collect(Collectors.toList());
        return answerReseponseDtoList;
    }


    // 내 답변 상세 조회 -> 질문 내용 & 댓글 내용들까지 넘겨줌.
    @Transactional(readOnly = true)
    @Override
    public AnswerWithCommentsDto getAnswer(Long answerId, UserDetailsImpl userDetails) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 답변입니다."));

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for(Comment comment : answer.getComments()){
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment.getId(), comment.getContent());
            commentResponseDtoList.add(commentResponseDto);
        }

        return new AnswerWithCommentsDto(answer.getId(), answer.getContent(), answer.getLikes(), commentResponseDtoList);
    }
}

