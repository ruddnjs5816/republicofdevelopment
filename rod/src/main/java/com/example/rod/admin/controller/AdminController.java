package com.example.rod.admin.controller;

import com.example.rod.admin.dto.AdminSigninRequestDto;
import com.example.rod.admin.service.AdminService;
import com.example.rod.admin.dto.AdminSignupRequestDto;
import com.example.rod.answer.service.AnswerService;
import com.example.rod.comment.service.CommentService;
import com.example.rod.product.dto.ProductModifyRequestDto;
import com.example.rod.product.dto.ProductRequestDto;
import com.example.rod.product.service.ProductService;
import com.example.rod.question.service.QuestionService;
import com.example.rod.security.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ProductService productService;
    private final CommentService commentService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    // admin 회원가입
    @PostMapping("/admin/auth/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void adminSignup(@RequestBody AdminSignupRequestDto adminSignupDto){
        adminService.signUp(adminSignupDto);
    }

    // admin 로그인
    @PostMapping("/admin/auth/signin")
    @ResponseStatus(HttpStatus.OK)
    public void adminSignin(@RequestBody AdminSigninRequestDto adminSigninDto, HttpServletResponse response){
        adminService.signIn(adminSigninDto, response);
    }


    //상품 수정
    @PutMapping("/admin/shop/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductModifyRequestDto productModifyRequestDto, List<MultipartFile> productImgFileList, Model model
//            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            productService.updateProduct(productId, productModifyRequestDto, productImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
        }
    }

    //상품 삭제
    @DeleteMapping("/admin/shop/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    //질문 삭제
    @DeleteMapping("/admin/questions/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@PathVariable Long questionId,
                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        adminService.deleteQuestion(questionId, userDetails);
    }

    //답변 삭제
    @DeleteMapping("/admin/answers/{answerId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnswer(@PathVariable Long answerId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        adminService.deleteAnswer(answerId, userDetails);
    }

    //댓글 삭제
    @DeleteMapping("/admin/answers/{answerId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long commentId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        adminService.deleteComment(commentId, userDetails);
    }

    //유저 수 조회
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Long countUsers(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return adminService.countUsers(userDetails);
    }

    //유저 포인트 회수

}
