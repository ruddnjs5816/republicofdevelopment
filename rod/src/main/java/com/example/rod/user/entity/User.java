package com.example.rod.user.entity;


import com.example.rod.answer.entity.Answer;
import com.example.rod.comment.entity.Comment;
import com.example.rod.exception.OutOfStockException;
import com.example.rod.order.entity.Order;
import com.example.rod.question.entity.Question;
import com.example.rod.share.TimeStamped;
import com.example.rod.profile.dto.ProfileRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;

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

    @Column(nullable = false, unique = true)
    private String username;

    private String nickname;
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
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questionList;

    @JsonBackReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)    /* orphanRemoval 쓸 것인지 생각 */
    private List<Answer> answerList;

    @JsonBackReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList;

    private String imageUrl;
    private String filename;

    private String adminToken;

    private String introduce;
    private String githunAddress;


    @Builder
    public User(String username, String nickname, String password, Integer point,
                String phoneNumber, Integer rating, UserGrade grade, UserRole role,
                String imageUrl, String filename, String adminToken, String introduce,
                String githunAddress) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.point = point;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.grade = grade;
        this.role = role;
        this.imageUrl = imageUrl;
        this.filename = filename;
        this.adminToken = adminToken;
        this.introduce = introduce;
        this.githunAddress = githunAddress;
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

    public void changeProfile(String nickname, String password, String phoneNumber,
                              String githunAddress, String introduce) {
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.githunAddress = githunAddress;
        this.introduce = introduce;
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

    public void setImageUrl(String storedFileName) {
        this.imageUrl = storedFileName;
    }

    //포인트 관련 메서드

    //포인트 환불
    public void refundPoint(int price) {
        this.point += price;
    }
    //포인트 차감
    public void payPoint(int price) {
        int restPoint = this.point - price;
        if (restPoint < 0) {
            throw new OutOfStockException("유저의 포인트가 부족합니다.(현재 포인트: " + this.point + ")");
        }
        this.point = restPoint;
    }

    //admin판단 메소드
    public Boolean isRealAdmin(String secretKey){
        role = this.getRole();
        adminToken = this.getAdminToken();
        if(!role.equals(UserRole.ADMIN)){
            if(!adminToken.matches(secretKey)){
                throw new IllegalArgumentException("관리자만 이용할 수 있는 기능입니다.");
            }
        }
        return true;
    }
}
