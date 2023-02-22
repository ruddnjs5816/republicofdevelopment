package com.example.rod.file.controller;

import com.example.rod.file.dto.FileDto;
import com.example.rod.profile.dto.ProfileRequestDto;
import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final UserService userService;


    @PostMapping("/files")
    public void fileUpload(FileDto fileDto){

    }

    @ResponseBody   // Long 타입을 리턴하고 싶은 경우 붙여야 함 (Long - 객체)
    @PostMapping(value = "/users/mypage/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveProfileImage(HttpServletRequest request,
                                 @RequestParam(value="image") MultipartFile image,
//                                 ProfileRequestDto profileRequestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        userService.saveProfileImage(image, userDetails);
    }

}
