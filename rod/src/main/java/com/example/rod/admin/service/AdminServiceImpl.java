package com.example.rod.admin.service;

import com.example.rod.admin.dto.AdminSigninRequestDto;
import com.example.rod.admin.dto.AdminSignupRequestDto;
import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.repository.CommentRepository;
import com.example.rod.product.dto.ProductModifyRequestDto;
import com.example.rod.product.service.ProductService;
import com.example.rod.question.entity.Question;
import com.example.rod.question.repository.QuestionRepository;
import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.security.exception.CustomException;
import com.example.rod.security.jwt.JwtUtil;
import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserRole;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.example.rod.security.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ProductService productService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    //admin 회원가입
    @Override
    @Transactional
    public void signUp(AdminSignupRequestDto adminSignupRequestDto) {
        String username = adminSignupRequestDto.getUsername();
        String password = passwordEncoder.encode(adminSignupRequestDto.getPassword());
        UserRole role = UserRole.ADMIN;

        //admin name 중복검사
        validateusername(username);

        //new admin 생성
        User admin = User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();

        //admin repository에 저장
        userRepository.save(admin);
    }

    //admin 로그인
    @Override
    @Transactional
    public void signIn(AdminSigninRequestDto adminSigninRequestDto, HttpServletResponse response) {
        String username = adminSigninRequestDto.getUsername();
        String password = adminSigninRequestDto.getPassword();

        // ADMIN 확인
        User admin = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(NOT_FOUND_ADMIN)
        );

        //비밀번호 일치 확인
        validatePassword(password, admin.getPassword());

        //AUTHORIZATION_HEADER: KEY 값
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(admin.getUsername(), UserRole.ADMIN));
    }

    // 상품 수정
    @Override
    @Transactional
    public void updateProduct(Long productId, ProductModifyRequestDto productModifyRequestDto, List<MultipartFile> productImgFileList, Model model) throws Exception {
        productService.updateProduct(productId,  productModifyRequestDto, productImgFileList);
    }

    //admin 댓글 삭제
    @Override
    @Transactional
    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        Comment targetComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT)
        );
        commentRepository.delete(targetComment);
    }

    //admin 답변 삭제
    @Override
    @Transactional
    public void deleteAnswer(Long answerId, UserDetailsImpl userDetails) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("답변이 존재하지 않습니다.")
        );
        answerRepository.delete(answer);
    }

    @Override
    @Transactional
    public Long countUsers(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        if(!user.isRealAdmin(secretKey)){
            throw new IllegalArgumentException("관리자만 이용할 수 있는 기능입니다.");
        }
        return Long.valueOf(userRepository.countUsersByRole(UserRole.USER));

    }

    //admin 질문 삭제
    @Override
    @Transactional
    public void deleteQuestion(Long questionId, UserDetailsImpl userDetails) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new CustomException(NOT_FOUND_QUESTION)
        );
        questionRepository.delete(question);
    }

    //admin name 검증
    @Transactional
    public void validateusername(String username) {
        User admin = userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("이미 존재하는 ADMIN ID입니다.")
        );
    }

    //admin password 검증
    @Transactional
    public void validatePassword(String password, String encodedPassword){
        if(!passwordEncoder.matches(password, encodedPassword)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


}
