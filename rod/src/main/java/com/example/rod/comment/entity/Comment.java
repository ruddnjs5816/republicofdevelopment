package com.example.rod.comment.entity;

import com.example.rod.answer.entity.Answer;
import com.example.rod.share.TimeStamped;
import lombok.AllArgsConstructor;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    private Answer answer;


    public Comment(String content) {
        this.content = content;
    }

    public void setFK(Answer answer){
        this.answer = answer;
    }

    public void updateContent(String content) { this.content = content; }


}
