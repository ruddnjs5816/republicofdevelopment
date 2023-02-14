package com.example.rod.user.entity;

import com.example.rod.answer.entity.Answer;
import com.example.rod.order.entity.Order;
import com.example.rod.profile.entity.Profile;
import com.example.rod.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String password;

    private Long point;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private GradeType grade;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();


    // 일대일 단방향 매핑 : 유저는 자기 아이디에 맞는 프로필을 소유한다.
    @OneToOne
    @JoinColumn(name="profile_id")
    private Profile profile;


    //order
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
