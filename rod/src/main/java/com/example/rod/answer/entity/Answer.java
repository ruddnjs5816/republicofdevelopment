package com.example.rod.answer.entity;

//import com.example.rod.comment.entity.Comment;
import com.example.rod.comment.entity.Comment;
import com.example.rod.question.entity.Question;
import com.example.rod.share.TimeStamped;
import com.example.rod.user.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
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

//    private String originalFileName;
//    private String savedFileName;

//    @OneToMany(mappedBy = "Answer", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    @OrderBy("createdAt DESC")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private Question question;


    public Answer(String content) {
        this.content = content;
    }

    public void setFK(Question question) {
        this.question = question;
    }


    public void updateContent(String content){
        this.content= content;
    }

    public void setLikes(int likes){
        this.likes = likes;
    }

}
