package com.example.rod.user.entity;

import lombok.Getter;
import org.apache.tomcat.jni.Address;

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

    private Address address;

    private Long point;

    private String phonenumber;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public User(Long userId, String username, String nae, String password, Address address, Long point, String phonenumber, RoleType role) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.password = password;
        this.address = address;
        this.point = point;
        this.phonenumber = phonenumber;
        this.role = role;
    }

}
