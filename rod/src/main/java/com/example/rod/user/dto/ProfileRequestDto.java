package com.example.rod.user.dto;

import com.example.rod.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {

    private String username;

    private String password;

    private String phoneNumber;

    public ProfileRequestDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();

    }
}
