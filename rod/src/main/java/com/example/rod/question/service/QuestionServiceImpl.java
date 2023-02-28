package com.example.rod.question.service;

import com.example.rod.answer.dto.AnswerWithCommentsDto;
import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.comment.dto.CommentResponseDto;
import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.repository.CommentRepository;
import com.example.rod.question.dto.*;
import com.example.rod.question.entity.Question;
import com.example.rod.question.entity.QuestionHashTag;
import com.example.rod.question.repository.QuestionHashTagRepository;
import com.example.rod.question.repository.QuestionRepository;
import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.hibernate.sql.Delete;
import org.hibernate.sql.Select;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final QuestionHashTagRepository questionHashTagRepository;

    private final QuestionHashTagService questionHashTagService;


    @Override
    @Transactional
    public CreateQuestionResponseDto createQuestion(QuestionRequest questionRequest, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        String hashtagStrs = questionRequest.getHashtagStrs();

        Question question = Question.builder()
                .title(questionRequest.getTitle())
                .content(questionRequest.getContent())
                .user(user)
                .isClosed(false)
                .difficulty(0d) //  기본 난이도 0으로 고정.
                .build();

        questionRepository.save(question);
        // 태그 저장
        questionHashTagService.saveHashTags(question, hashtagStrs);

        return new CreateQuestionResponseDto(question.getQuestionId());
    }

    @Override
    @Transactional
    public GetQuestionsResponse getMyQuestions(UserDetailsImpl userDetails, Pageable pageable, int page) {

        User user = userDetails.getUser();

        Page<Question> questionList = questionRepository.findAllByUser(user, pageable.withPage(page - 1));

        // query를 가져오는 방법
        // 트러블 슈팅 해볼만 함 -> Question - Count Mapping Table 만들기? 그래서 1씩 증가 시키기. <- 이거 수치화 해보기.
        Long totalQuestionCount = questionRepository.countAllByUser(user);


        List<QuestionResponse> questionResponseList = new ArrayList<>();

        for (Question question : questionList) {
            questionResponseList.add(QuestionResponse.builder()
                    .questionId(question.getQuestionId())
                    .title(question.getTitle())
                    .nickname(question.getUser().getNickname())

                    .answerCount(question.getAnswersList().size())
                    .createdAt(question.getCreatedAt()).build());
        }
        return new GetQuestionsResponse(page, totalQuestionCount, questionResponseList);
    }

    @Override
    @Transactional
    public GetQuestionsResponse getQuestions(Pageable pageable, int page) {

        Page<Question> questionList = questionRepository.findAll(pageable.withPage(page - 1));

        // query를 가져오는 방법
        // 트러블 슈팅 해볼만 함 -> Question - Count Mapping Table 만들기? 그래서 1씩 증가 시키기. <- 이거 수치화 해보기.
        Long totalQuestionCount = questionRepository.count();

        List<QuestionResponse> questionResponseList = new ArrayList<>();

        for (Question question : questionList) {

            questionResponseList.add(QuestionResponse.builder()
                    .questionId(question.getQuestionId())
                    .title(question.getTitle())
                    .nickname(question.getUser().getNickname())
                    .answerCount(question.getAnswersList().size())
                    .createdAt(question.getCreatedAt()).build());
        }
        return new GetQuestionsResponse(page, totalQuestionCount, questionResponseList);
    }


    @Override
    @Transactional
    public QuestionWithAnswersResponse getSpecificQuestion(Long questionId, int page, int size) {
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
        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "likes"));
        Page<Answer> otherAnswers = answerRepository.findByQuestionAndIsSelected(question, false, pageRequest);

        for (Answer answer : otherAnswers) {
            answerWithComments.add(convertToAnswerWithCommentsDto(answer));
        }

        Long totalAnswerCount = answerRepository.countByQuestionQuestionId(questionId);

        QuestionWithAnswersResponse questionWithAnswersResponse = QuestionWithAnswersResponse.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .tagList(hashTagDto)
                .createdAt(question.getCreatedAt())
                .totalAnswerCount(totalAnswerCount)
                .difficulty(question.getDifficulty())
                .nickname(question.getUser().getNickname())
                .answerWithComments(answerWithComments)
                .build();

        return questionWithAnswersResponse;
    }

    public AnswerWithCommentsDto convertToAnswerWithCommentsDto(Answer answer) {
        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment comment : answer.getComments()) {
            CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                    .commentId(comment.getId())
                    .nickName(comment.getUser().getNickname())
                    .content(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .build();
            comments.add(commentResponseDto);
        }
        AnswerWithCommentsDto answerWithCommentsDto = AnswerWithCommentsDto.builder()
                .answerId(answer.getId())
                .nickname(answer.getUser().getNickname())
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .isSelected(answer.isSelected())
                .likes(answer.getLikes())
                .commentResponseDtoList(comments)
                .build();

        return answerWithCommentsDto;
    }


    @Override
    @Transactional
    public SelectAnswerForQuestionResponseDto selectAnswerForQuestion(Long questionId, Long answerId, UserDetailsImpl userDetails) {

        User questioner = userDetails.getUser();

        Question question = questionRepository.findById(questionId).orElseThrow
                (() -> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));

        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 아이디의 답변이 없습니다.")
        );

        question.processSelectionResult(questioner, question, answer);

        return new SelectAnswerForQuestionResponseDto(answerId);
    }


    // 질문 수정
    @Override
    @Transactional
    public ChangeQuestionResponseDto changeQuestion(Long questionId, ChangeQuestionRequest changeQuestionRequest, UserDetailsImpl userDetails) {
        Question question = questionRepository.findById(questionId).orElseThrow
                (() -> new IllegalArgumentException("해당 아이디의 질문이 없습니다."));
        User user = userDetails.getUser();
        question.editQuestion(user, changeQuestionRequest.getTitle(), changeQuestionRequest.getContent());



        return new ChangeQuestionResponseDto(questionId);
    }


    //질문 삭제
    @Override
    @Transactional
    public DeleteQuestionResponseDto deleteQuestion(Long questionId, UserDetailsImpl userDetails) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 질문이 없습니다.")
        );

        User user = userDetails.getUser();

        if (question.isOwnedBy(user)) {
            List<Long> hashtagIds = questionHashTagService.findTagIdsByQuestionId(questionId);
            questionRepository.deleteById(questionId);
            questionHashTagService.deleteTagsIfNotExistMatchingQuestion(hashtagIds);
        } else {
            throw new IllegalArgumentException("삭제 권한이 없는 유저입니다.");
        }

        return new DeleteQuestionResponseDto(questionId);
    }
    // 질문 검색 API ( 제목으로 검색 )

    @Override
    @Transactional
    public GetQuestionsResponse searchQuestion(Optional<String> title, Optional<String> nickname, Optional<String> hashtagname,
                                               int page, Pageable pageable) {

        List<Question> questionList = new ArrayList<>();
        // query를 가져오는 방법
        // 트러블 슈팅 해볼만 함 -> Question - Count Mapping Table 만들기? 그래서 1씩 증가 시키기. <- 이거 수치화 해보기.
        Long totalQuestionCount = 0L;


        if (title.isPresent()) {
            Page<Question> pageQ = questionRepository.findByTitleContaining(title.get(), pageable.withPage(page - 1));
            totalQuestionCount = questionRepository.countByTitleContaining(title.get());
            questionList.addAll(pageQ.getContent());
        } else if (nickname.isPresent()) {
            Page<Question> pageQ = questionRepository.findByUserNicknameContaining(nickname.get(), pageable.withPage(page - 1));
            totalQuestionCount = questionRepository.countByUserNicknameContaining(nickname.get());
            questionList.addAll(pageQ.getContent());
        } else if (hashtagname.isPresent()) {
            Page<QuestionHashTag> questionHashtags = questionHashTagRepository.findByHashTag_HashTagName(hashtagname.get(), pageable.withPage(page - 1));
            totalQuestionCount = questionHashTagRepository.countByHashTag_HashTagName(hashtagname.get());
            for (QuestionHashTag questionHashtag : questionHashtags) {
                questionList.add(questionHashtag.getQuestion());
            }
        }

        Page<Question> questionListWithPage = new PageImpl<>(questionList, pageable, questionList.size());

        List<QuestionResponse> questionResponseList = questionListWithPage.stream()
                .map(question -> QuestionResponse.builder()
                        .questionId(question.getQuestionId())
                        .title(question.getTitle())
                        .nickname(question.getUser().getNickname())
                        .answerCount(question.getAnswersList().size())
                        .createdAt(question.getCreatedAt()).build())
                .collect(Collectors.toList());

        GetQuestionsResponse response = new GetQuestionsResponse(page, totalQuestionCount, questionResponseList);

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

