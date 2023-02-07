package com.example.rod.question.entity;

import com.example.rod.question.dto.QuestionRequest;
import com.example.rod.share.TimeStamped;
import com.example.rod.user.entity.userEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Question extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long questionId;

    @Column
    private String title;

    @Column
    private String content;

//    private TagEnum tag; // 태그 테이블을 만들어야할까?, 만든다면, 태그 테이블 어떻게 운영?

    @ManyToOne
    @JoinColumn(name="user_id")
    private userEntity user;




    public Question(QuestionRequest questionRequest){
        this.title = questionRequest.getTitle();
        this.content = questionRequest.getContent();
    }

    public void edit(String title, String content){
        this.title = title;
        this.content = content;
    }



}
