package com.example.rod.question.entity;

import com.example.rod.question.dto.QuestionRequest;
import com.example.rod.share.TimeStamped;
import com.example.rod.user.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Answer> answers;


    public Question(QuestionRequest questionRequest){
        this.title = questionRequest.getTitle();
        this.content = questionRequest.getContent();
    }

    public void editTitle(String title){
        this.title = title;
    }

    public void editContent(String content){
        this.content = content;
    }



}
