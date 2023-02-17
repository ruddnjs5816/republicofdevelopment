package com.example.rod.user.entity;


import com.example.rod.answer.entity.Answer;
import com.example.rod.comment.entity.Comment;
import com.example.rod.question.entity.Question;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
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

    @Column(nullable = false)
    @ColumnDefault("0")
    private int point;

    private String phoneNumber;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int rating;

    @Enumerated(value = EnumType.STRING)
    private UserGrade grade;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @JsonBackReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questionList;

    @JsonBackReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)    /* orphanRemoval 쓸 것인지 생각 */
    private List<Answer> answerList;

    @JsonBackReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList;



    @Builder
    public User(String username, String name, String password, Integer point, String phoneNumber, Integer rating, UserGrade grade, UserRole role) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.point = point;
        this.phoneNumber = phoneNumber;
        rating = rating;
        this.grade = grade;
        this.role = role;
    }

    @Override
    public boolean equals(Object obj){
        return this.userId == ((User) obj).getUserId();
    }

    public void increaseRating(int amount){
        rating += amount;
    }

    public void increasePoint(int amount){
        this.point += amount;
    }


//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Question> questions = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Answer> answers = new ArrayList<>();


//    public void changeGrade(UserGrade){
//        this.grade = UserGrade.valueOf(role);
//    }

    public void update(String username, String name, String password, String phoneNumber) {
        this.username = username;
        this.name= name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // 질문 등록, 답변 등록, 답변 채택 -> 실행

    public void changeUserGrade(int rating){
        // 등급 상승 비즈니스 로직
        if(rating<=50){
            grade = UserGrade.valueOf("BRONZE");
        } else if (50 < rating && rating<=150) {
            grade = UserGrade.valueOf("SILVER");
        } else if (151<rating && rating<=300) {
            grade = UserGrade.valueOf("GOLD");
        } else if (301<rating && rating<=500) {
            grade = UserGrade.valueOf("PLATINUM");
        } else if (501<rating && rating<=750) {
            grade = UserGrade.valueOf("DIAMOND");
        } else if (751<rating && rating<=1050) {
            grade = UserGrade.valueOf("MASTER");
        } else {
            grade = UserGrade.valueOf("GRANDMASTER");
        }
        
    }

}
