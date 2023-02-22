package com.example.rod.question.service;

import com.example.rod.answer.dto.AnswerWithCommentsDto;
import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.comment.dto.CommentResponseDto;
import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.repository.CommentRepository;
import com.example.rod.exception.GetException;
import com.example.rod.question.dto.*;
import com.example.rod.question.entity.Question;
import com.example.rod.question.entity.QuestionHashTag;
import com.example.rod.question.repository.QuestionHashTagRepository;
import com.example.rod.question.repository.QuestionRepository;
import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.*;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.rod.exception.StatusExceptionCode.FILE_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final QuestionHashTagRepository questionHashTagRepository;

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

        // 1. Question 의 Tag들 조회.
        HashTagDto hashTagDto = questionHashTagService.findTagsByQuestionId(questionId);

        List<AnswerWithCommentsDto> answerWithComments = new ArrayList<>();

        // 2. 채택된 답변(is_selected = true)을 먼저 가져온다.
        List<Answer> selectedAnswers = answerRepository.findByQuestionAndIsSelected(question, true);
        for (Answer answer : selectedAnswers) {
            answerWithComments.add(convertToAnswerWithCommentsDto(answer));
        }

        // 3. 나머지 답변들을 좋아요 수에 따라 정렬해서 가져온다.
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "likes"));
        Page<Answer> otherAnswers = answerRepository.findByQuestionAndIsSelected(question, false, pageRequest);

        for (Answer answer : otherAnswers) {
            answerWithComments.add(convertToAnswerWithCommentsDto(answer));
        }

        QuestionWithAnswersResponse questionWithAnswersResponse = new QuestionWithAnswersResponse
                (question.getTitle(), question.getContent(), hashTagDto, question.getDifficulty(), answerWithComments);

        return questionWithAnswersResponse;
    }

    public AnswerWithCommentsDto convertToAnswerWithCommentsDto(Answer answer) {
        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment comment : answer.getComments()) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment.getId(), comment.getContent());
            comments.add(commentResponseDto);
        }
        AnswerWithCommentsDto answerWithCommentsDto = new AnswerWithCommentsDto(answer.getId(), answer.getContent(), answer.isSelected(), answer.getLikes(), comments);
        return answerWithCommentsDto;
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
    }
    // 질문 검색 API ( 제목으로 검색 )

    @Override
    @Transactional
    public GetQuestionsResponse searchQuestion(Optional<String> title, Optional<String> nickname, Optional<String> hashtagname,
                                               int page, Pageable pageable) {

        List<Question> questionList = new ArrayList<>();

        if (title.isPresent()) {
            Page<Question> pageQ = questionRepository.findByTitleContaining(title.get(), pageable.withPage(page - 1));
            questionList.addAll(pageQ.getContent());
        } else if (nickname.isPresent()) {
            Page<Question> pageQ = questionRepository.findByUser_NameContaining(nickname.get(), pageable.withPage(page - 1));
            questionList.addAll(pageQ.getContent());
        } else if (hashtagname.isPresent()) {
            Page<QuestionHashTag> questionHashtags = questionHashTagRepository.findByHashTag_HashTagName(hashtagname.get(), pageable.withPage(page - 1));

            for (QuestionHashTag questionHashtag : questionHashtags) {
                questionList.add(questionHashtag.getQuestion());
            }
        }

        Page<Question> questionListWithPage = new PageImpl<>(questionList, pageable, questionList.size());

        List<QuestionResponse> questionResponseList = questionListWithPage.stream()
                .map(question -> QuestionResponse.builder()
                        .questionId(question.getId())
                        .title(question.getTitle())
                        .nickname(question.getUser().getName())
                        .answerCount(question.getAnswers().size())
                        .createdAt(question.getCreatedAt()).build())
                .collect(Collectors.toList());

        GetQuestionsResponse response = new GetQuestionsResponse(page, questionResponseList);

        return response;
    }

//    @Override

    // public void uploadImage(MultipartFile image) throws GetException {}

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

