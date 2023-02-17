package com.example.rod.comment.entity;

import com.example.rod.answer.entity.Answer;
import com.example.rod.question.entity.Question;
import com.example.rod.share.TimeStamped;
import com.example.rod.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    public Comment(User user, Answer answer, String content){
        this.user = user;
        this.answer = answer;
        this.content = content;
    }

    public boolean isOwnedBy(User user){
        return this.user.equals(user);
    }

    public void updateContent(String content) { this.content = content; }


}
