package com.example.rod.user.service;

import com.example.rod.aws.service.S3Uploader;
import com.example.rod.file.service.FileService;
import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.user.dto.InfoResponseDto;
import com.example.rod.profile.dto.ProfileRequestDto;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Validated //다른 계층에서 파라미터를 검증
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final FileService fileService;

    @Autowired
    private S3Uploader s3Uploader;
    // 내 프로필 조회
    @Transactional(readOnly = true)
    public InfoResponseDto getMyInfo(User user) {
        InfoResponseDto info = new InfoResponseDto(user);
        return info;
    }

    // 내 프로필 수정
    @Transactional
    public void editMyInfo(MultipartFile multipartFile, ProfileRequestDto profileRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        String uuid = UUID.randomUUID().toString();
        String unique = uuid.substring(0,7);
        String filename = unique + "-" + multipartFile.getOriginalFilename();
        String username = profileRequestDto.getName();
        String phoneNumber = profileRequestDto.getPhoneNumber();
        String password = profileRequestDto.getPassword();

//        user.changeProfile(password, phoneNumber, username, filename);
        fileService.upload(multipartFile, filename);
        userRepository.save(user);
    }

    // 내 프로필 이미지 저장
    // fileController 에서 설정
    @Transactional
    public void saveProfileImage(MultipartFile image,
                                 ProfileRequestDto profileRequestDto,
                                 UserDetailsImpl userDetails) throws IOException {
        String password = profileRequestDto.getPassword();
        String username = profileRequestDto.getName();
        String phoneNumber = profileRequestDto.getPhoneNumber();
        User user = userDetails.getUser();
        String filename = image.getName();
//        String filename = user.getFilename();
        user.setFilename(filename);

        try{
            user.setPassword(passwordEncoder.encode(password));
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            user.setPassword(userDetails.getPassword());
        }
        try{
            user.setUsername(username);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            user.setUsername(userDetails.getName());
        }
        try{
            user.setPhoneNumber(phoneNumber);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            user.setPhoneNumber(userDetails.getPhoneNumber());
        }

        if(!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image,"images");
            user.setImageUrl(storedFileName);
        }
        userRepository.save(user);
    }
}
