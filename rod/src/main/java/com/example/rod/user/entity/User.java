package com.example.rod.user.entity;


import com.example.rod.question.entity.Question;
import com.example.rod.share.TimeStamped;
import com.example.rod.profile.dto.ProfileRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@Setter
public class User extends TimeStamped {

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

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer rating;

    @Enumerated(value = EnumType.STRING)
    private UserGrade grade;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @JsonBackReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> question;

    private String imageUrl;
    private String filename;


    @Builder
    public User(String username, String name, String password, Integer point,
                String phoneNumber, Integer rating, UserGrade grade, UserRole role,
                String imageUrl, String filename) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.point = point;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.grade = grade;
        this.role = role;
        this.imageUrl = imageUrl;
        this.filename = filename;
    }

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Question> questions = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Answer> answers = new ArrayList<>();


//    public void changeGrade(UserGrade){
//        this.grade = UserGrade.valueOf(role);
//    }

    public void changeProfile(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public void changeImage(String filename){
        this.filename = filename;
    };

    // 질문 등록, 답변 등록, 답변 채택 -> 실행

    public void changeGrade(UserGrade newGrade){
        this.grade = newGrade;
    }

    public void setImageUrl(String storedFileName) {
        this.imageUrl = storedFileName;
    }
}
