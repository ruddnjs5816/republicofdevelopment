package com.example.rod.user.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer point;

    private String phoneNumber;

    private Integer rating;

    @Enumerated(value = EnumType.STRING)
    private UserGrade grade;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String username, String name, String password, Integer point, String phoneNumber, Integer rating, UserGrade grade, UserRole role) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.point = point;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.grade = grade;
        this.role = role;
    }

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Question> questions = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Answer> answers = new ArrayList<>();

    public void changeRole(String role){
        this.grade = UserGrade.valueOf(role);
    }

    public void update(String username, String name, String password, String phoneNumber) {
        this.username = username;
        this.name= name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
