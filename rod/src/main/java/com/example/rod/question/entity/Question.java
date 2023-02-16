package com.example.rod.question.entity;

import com.example.rod.answer.entity.Answer;
import com.example.rod.question.dto.QuestionRequest;
import com.example.rod.share.TimeStamped;

import com.example.rod.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
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


    @JsonBackReference
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Answer> answers = new ArrayList<>();

  /*  @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionTag> questionTags = new ArrayList<>();

*/
    @Builder
    public Question(String title, String content, User user, List<Answer> answers){
        this.title = title;
        this.content = content;
        this.user = user;
        this.answers = answers;
//        this.questionTags = questionRequest.getTagList();
    }

//    public void setFK(User user){ this.user = user; }

    public void editTitle(String title){
        this.title = title;
    }

    public void editContent(String content){
        this.content = content;
    }

    public boolean isOwnedBy (User user) { return (this.user.equals(user)); }



}
