package com.example.rod.answer.entity;

//import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.entity.Comment;
import com.example.rod.question.entity.Question;
import com.example.rod.share.TimeStamped;
import com.example.rod.user.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Answer extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String content;
    @Column
    private int likes;

    @Column
    private boolean isSelected;



    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    @OrderBy("createdAt DESC")
    private List<Comment> comments = new ArrayList<>();



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;


    public Answer(String content) {
        this.content = content;
    }

    @Builder
    public Answer(String content, int likes, Question question, User user){
        this.content = content;
        this.likes = likes;
        this.question = question;
        this.user = user;
    }

    public boolean isOwnedBy(User user){
        return (this.user.equals(user)) ;
    }

    @Override
    public boolean equals(Object obj){
        return this.id == ((Answer)obj).getId();
    }


    public void updateContent(String content){
        this.content = content;
    }

    public void increaseLikes(){
        this.likes += 1;
    }

    public void decreaseLikes(){
        this.likes -= 1;
    }

    public void selected() { this.isSelected = true; }


}
