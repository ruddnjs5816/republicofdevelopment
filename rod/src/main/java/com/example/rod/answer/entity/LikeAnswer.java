
package com.example.rod.answer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class LikeAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ANSWERS_ID", nullable = false)

    private Answer answer;




    public LikeAnswer(Answer answer) {
        this.answer = answer;
    }
}

