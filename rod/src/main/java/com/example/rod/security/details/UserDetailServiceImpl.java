
package com.example.rod.security.details;

import com.example.rod.security.exception.CustomException;
import com.example.rod.security.exception.ErrorCode;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Primary
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        return UserDetailsImpl.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .grade(user.getGrade())
                .role(user.getRole())
                .authority(new SimpleGrantedAuthority(user.getRole().getAuthority()))
                .user(user)
                .build();
//        else{
//            Admin admin = adminRepository.findByusername(username).orElseThrow(
//                    ()->new CustomException(ErrorCode.NOT_FOUND_ADMIN)
//            );
//            AdminDetailsImpl resultDetail2 = AdminDetailsImpl.builder()
//                    .adminId(admin.getAdminId())
//                    .username(admin.getusername())
//                    .password(admin.getpassword())
//                    .role(admin.getRole())
//                    .authority(new SimpleGrantedAuthority(admin.getRole().getAuthority()))
//                    .build();
//            return resultDetail2;
//        }
    }
}
