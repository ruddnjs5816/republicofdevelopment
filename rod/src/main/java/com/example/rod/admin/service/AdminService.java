package com.example.rod.admin.service;

import com.example.rod.admin.dto.AdminSigninRequestDto;
import com.example.rod.admin.dto.AdminSignupRequestDto;
import com.example.rod.product.dto.ProductModifyRequestDto;
import com.example.rod.security.details.UserDetailsImpl;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AdminService {

    // admin 회원가입
    void signUp(AdminSignupRequestDto adminSignupRequestDto);

    //admin 로그인
    void signIn(AdminSigninRequestDto adminSigninRequestDto, HttpServletResponse response);

    // 비밀번호 검증
    void validatePassword(String password, String encodedPassword);

    // 아이디 검증
    void validateusername(String username);

    // 상품 업데이트
    void updateProduct(Long productId,
                       ProductModifyRequestDto productModifyRequestDto,
                       List<MultipartFile> productImgFileList,
                       Model model) throws Exception;


    void deleteComment(Long commentId, UserDetailsImpl userDetails);
    void deleteQuestion(Long questionId, UserDetailsImpl userDetails);
    void deleteAnswer(Long answerId, UserDetailsImpl userDetails);

    Long countUsers(UserDetailsImpl userDetails);
}
