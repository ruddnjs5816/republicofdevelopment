package com.example.rod.comment.entity;

import com.example.rod.answer.entity.AnswerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class commentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "ANSWERS_ID")
    private AnswerEntity answerEntity;


    public commentEntity(String content) {
        this.content = content;
    }
}
