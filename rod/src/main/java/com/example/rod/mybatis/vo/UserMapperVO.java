package com.example.rod.mybatis.vo;

import com.example.rod.answer.entity.Answer;
import com.example.rod.comment.entity.Comment;
import com.example.rod.question.entity.Question;
import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserGrade;
import com.example.rod.user.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Setter
public class UserMapperVO {
    private Long userId;
    private String username;
    private String name;
    private String password;
    private int point;
    private String phoneNumber;
    private int rating;

    private UserGrade grade;

    private UserRole role;

    private List<Question> questionList;
   /* orphanRemoval 쓸 것인지 생각 */
    private List<Answer> answerList;

    private List<Comment> commentList;
    private String imageUrl;
    private String filename;

    public UserMapperVO(Long userId, String username, String password){
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
}
