package com.example.rod.user.entity;

import com.example.rod.address.Address;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String name;

    private String password;



    private Long point;

    private String phonenumber;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public User(Long userId, String username, String name, String password, Long point, String phonenumber, RoleType role) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.password = password;

        this.point = point;
        this.phonenumber = phonenumber;
        this.role = role;
    }

    public User() {

    }
}
