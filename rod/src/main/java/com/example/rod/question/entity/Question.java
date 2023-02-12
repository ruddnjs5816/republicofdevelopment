package com.example.rod.question.entity;

import com.example.rod.answer.entity.Answer;
import com.example.rod.question.dto.QuestionRequest;
import com.example.rod.share.TimeStamped;

import com.example.rod.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionTag> questionTags = new ArrayList<>();


    public Question(QuestionRequest questionRequest){
        this.title = questionRequest.getTitle();
        this.content = questionRequest.getContent();
        this.questionTags = questionRequest.getTagList();
    }

    public void editTitle(String title){
        this.title = title;
    }

    public void editContent(String content){
        this.content = content;
    }



}
