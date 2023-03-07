package com.example.rod.oauth2.dto;

import com.example.rod.user.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String filename;

    public SessionUser(User user) {
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.filename = user.getFilename();
    }
}