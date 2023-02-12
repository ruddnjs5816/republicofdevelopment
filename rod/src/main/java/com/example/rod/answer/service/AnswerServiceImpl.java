package com.example.rod.answer.service;

import com.example.rod.answer.dto.AnswerRequestDto;
import com.example.rod.answer.dto.AnswerResponseDto;
import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.comment.dto.CommentResponseDto;
import com.example.rod.comment.dto.CommentResultDto;
import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.repository.CommentRepository;
import com.example.rod.question.entity.Question;
import com.example.rod.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {


    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Answer createAnswer(Long questionId, AnswerRequestDto answerRequestDto) {

        Answer answer = new Answer(answerRequestDto.getContent());
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 질문이 존재하지 않습니다.")
        );
        answer.setQuestion(question);
        answerRepository.save(answer);
        return answer;
    }


    @Transactional
    public AnswerResponseDto updateAnswer(Long answerId, AnswerRequestDto answerRequestDto) {
        Answer AnswerSaved = answerRepository.findById(answerId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        AnswerSaved.update(answerRequestDto.getContent());
        answerRepository.save(AnswerSaved);
        return new AnswerResponseDto(AnswerSaved.getContent());
    }

    @Transactional
    public String deleteAnswer(Long answerId) {
        answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 답변입니다."));
        answerRepository.deleteById(answerId);
        return "삭제 완료";
    }


    // 내 답변 상세 조회
    @Transactional(readOnly = true)
    public AnswerResponseDto getAnswer(Long answerId) {
        Answer Answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 답변입니다."));
        return new AnswerResponseDto(Answer.getContent(), Answer.getLikes());
    }


    @Transactional(readOnly = true)
    public CommentResultDto getListAnswer(Pageable pageable, int page) {
        List<AnswerResponseDto> resultList = new ArrayList<>();
        Page<Answer> answers = answerRepository.findAll(pageable.withPage(page - 1));
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<Comment> allComment = commentRepository.findAll();
        for (Comment Comment : allComment) {
            commentResponseDtoList.add(new CommentResponseDto(Comment.getId(), Comment.getContent()));
        }

        for (Answer answer1 : answers) {
            AnswerResponseDto answerResponseDto =
                    new AnswerResponseDto(answer1.getContent(), answer1.getLikes(), commentResponseDtoList);
            resultList.add(answerResponseDto);
        }

        CommentResultDto resultDto = new CommentResultDto(page, resultList);
        return resultDto;

    }
}

