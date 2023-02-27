package com.example.rod.admin.entity;

import com.example.rod.share.TimeStamped;
import com.example.rod.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "admins")
@Getter
@NoArgsConstructor
public class Admin extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    private String adminName;

    private String adminPassword;

    private UserRole role = UserRole.ADMIN;


    @Builder
    public Admin(String adminName, String adminPassword, UserRole role){
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.role = role;
    }
}
