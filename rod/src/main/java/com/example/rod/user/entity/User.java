package com.example.rod.user.entity;

import com.example.rod.answer.entity.Answer;
import com.example.rod.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String name;

    private String password;

    private Long point;

    public User(String username, String name, String password, String phonenumber) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.phonenumber = phonenumber;
    }

    private String phonenumber;

    private Long rating;

    @Enumerated(EnumType.STRING)
    private GradeType grade;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public void changeRole(String role){
        this.grade = GradeType.valueOf(role);
    }
}
