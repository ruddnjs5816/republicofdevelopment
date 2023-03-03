package com.example.rod.user.service;

import com.example.rod.aws.service.S3Uploader;
import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.profile.dto.ProfileRequestDto;
import com.example.rod.user.dto.MyInfoResponseDto;
import com.example.rod.user.dto.OtherInfoResponseDto;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Validated //다른 계층에서 파라미터를 검증
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final S3Uploader s3Uploader;

    // 내 프로필 조회
    @Transactional(readOnly = true)
    public MyInfoResponseDto getMyInfo(User user) {
        MyInfoResponseDto info = new MyInfoResponseDto(user);
        return info;
    }

    // 내 프로필 수정
    @Transactional
    public void editMyInfo(ProfileRequestDto profileRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        String nickname = profileRequestDto.getNickname();
        String phoneNumber = profileRequestDto.getPhoneNumber();
        String password = profileRequestDto.getPassword();
        String githubAddress = profileRequestDto.getGithubAddress();
        String introduce = profileRequestDto.getIntroduce();
        String email = profileRequestDto.getEmail();
        user.changeProfile(nickname, password, phoneNumber, githubAddress, introduce, email);
//        user.changeProfile(password, phoneNumber, username, filename);
        userRepository.save(user);
    }

    // 내 프로필 이미지 저장
    // fileController 에서 설정
    @Transactional
    public void saveProfileImage(MultipartFile image,
                                 UserDetailsImpl userDetails) throws IOException {
        User user = userDetails.getUser();
        String uuid = UUID.randomUUID().toString().substring(0,7);
        String filename = uuid+"-"+image.getOriginalFilename();
        user.setFilename(filename);

        if(!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image,"images");
            user.setImageUrl(storedFileName);
        }
        userRepository.save(user);
    }
    
    //타인 프로필 조회
    @Transactional(readOnly = true)
    public OtherInfoResponseDto getOtherInfo(Long userId) {
        User user = userRepository.findByUserId(userId);
        OtherInfoResponseDto info = new OtherInfoResponseDto(user);
        return info;
    }
}
