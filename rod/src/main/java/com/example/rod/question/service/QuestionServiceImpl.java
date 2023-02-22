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

import lombok.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final QuestionHashTagService questionHashTagService;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public void createQuestion(QuestionRequest questionRequest, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        String hashtagStrs = questionRequest.getHashtagStrs();

        Question question = Question.builder()
                .title(questionRequest.getTitle())
                .content(questionRequest.getContent())
                .user(user)
                .isClosed(false)
                .difficulty(0f) //  기본 난이도 0으로 고정.
                .build();

        questionRepository.save(question);
        // 태그 저장
        questionHashTagService.saveHashTags(question, hashtagStrs);
    }

    @Override
    @Transactional
    public GetQuestionsResponse getMyQuestions(UserDetailsImpl userDetails, Pageable pageable, int page) {

        User user = userDetails.getUser();

        Page<Question> questionList = questionRepository.findAllByUser(user, pageable.withPage(page - 1));

        List<QuestionResponse> questionResponseList = new ArrayList<>();

        for (Question question : questionList) {
            questionResponseList.add(QuestionResponse.builder()
                    .questionId(question.getId())
                    .title(question.getTitle())
                    .nickname(question.getUser().getName())
                    .answerCount(question.getAnswers().size())
                    .createdAt(question.getCreatedAt()).build());
        }
        return new GetQuestionsResponse(page, questionResponseList);
    }

    @Override
    @Transactional
    public GetQuestionsResponse getQuestions(Pageable pageable, int page) {

        Page<Question> questionList = questionRepository.findAll(pageable.withPage(page - 1));


        List<QuestionResponse> questionResponseList = new ArrayList<>();


        for (Question question : questionList) {
            questionResponseList.add(QuestionResponse.builder()
                    .questionId(question.getId())
                    .title(question.getTitle())
                    .nickname(question.getUser().getName())
                    .answerCount(question.getAnswers().size())
                    .createdAt(question.getCreatedAt()).build());
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

        for (Answer answer : answerList) {
            List<CommentResponseDto> comments = new ArrayList<>();
            for (Comment comment : answer.getComments()) {
                CommentResponseDto commentResponseDto = new CommentResponseDto(comment.getId(), comment.getContent());
                comments.add(commentResponseDto);
            }
            AnswerWithCommentsDto answerWithCommentsDto = new AnswerWithCommentsDto(answer.getId(), answer.getContent(), answer.getLikes(), comments);
            answerWithComments.add(answerWithCommentsDto);
        }

        // 3. Question 의 Tag들 조회.
        HashTagDto hashTagDto = questionHashTagService.findTagsByQuestionId(questionId);

        QuestionWithAnswersResponse questionWithAnswersResponse = new QuestionWithAnswersResponse(question.getTitle(), question.getContent(), question.getDifficulty(), answerWithComments, hashTagDto);

        return questionWithAnswersResponse;
    }


    @Override
    @Transactional
    public void selectAnswerForQuestion(Long questionId, Long answerId, UserDetailsImpl userDetails) {

        User questioner = userDetails.getUser();

        Question question = questionRepository.findById(questionId).orElseThrow
                (() -> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));

        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 아이디의 답변이 없습니다.")
        );

        question.processSelectionResult(questioner, question, answer);
    }


    @Override
    @Transactional
    public void changeQuestionTitle(Long questionId, PatchQuestionTitleRequest patchQuestionTitleRequest, UserDetailsImpl userDetails) {
        Question question = questionRepository.findById(questionId).orElseThrow
                (() -> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));
        User user = userDetails.getUser();
        question.editTitle(user, patchQuestionTitleRequest.getTitle());
    }

    // 질문 내용 변경
    @Override
    @Transactional
    public void changeQuestionContent(Long questionId, PatchQuestionContentRequest patchQuestionContentRequest, UserDetailsImpl userDetails) {
        Question question = questionRepository.findById(questionId).orElseThrow
                (() -> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));
        User user = userDetails.getUser();
        question.editContent(user, patchQuestionContentRequest.getContent());
    }


    //질문 삭제
    @Override
    @Transactional
    public void deleteQuestion(Long questionId, UserDetailsImpl userDetails) {


        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 질문이 없습니다.")
        );
        User user = userDetails.getUser();

        if (question.isOwnedBy(user)) {
            questionRepository.deleteById(questionId);
        } else {
            throw new IllegalArgumentException("삭제 권한이 없는 유저입니다.");
        }


    /*// 질문에 이미지 업로드
    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;
    public void uploadImage(MultipartFile image){
        Path copyOfLocation = Paths.get(uploadDir + File.separator +  StringUtils.cleanPath(image.getOriginalFilename()));
        try {
            // inputStream을 가져와서
            // copyOfLocation (저장위치)로 파일을 쓴다.
            // copy의 옵션은 기존에 존재하면 REPLACE(대체한다), 오버라이딩 한다
            Files.copy(image.getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file : " + image.getOriginalFilename());
        }
    }*/
    }
}
