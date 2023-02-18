package com.example.rod.question.entity;

import com.example.rod.answer.entity.Answer;
import com.example.rod.question.dto.QuestionRequest;
import com.example.rod.share.TimeStamped;

import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserGrade;
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

    @Column
    private boolean isClosed;   // 채택마감여부


    @Column
    private double difficulty;   //  난이도

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


    @JsonBackReference
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.REMOVE })
    private List<Answer> answers = new ArrayList<>();

    // question에서 어떤 행위를 할 때, answer 를 쓸 건지의 여부에 따라 이 연관관계를 ....

  /*  @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionTag> questionTags = new ArrayList<>();

*/
    @Builder
    public Question(String title, String content, User user, List<Answer> answers, boolean isClosed, float difficulty){
        this.title = title;
        this.content = content;
        this.user = user;
        this.answers = answers;
        this.isClosed = isClosed;
        this.difficulty = difficulty;
//        this.questionTags = questionRequest.getTagList();
    }

    public void calculateDifficulty(double difficulty){

        // 이전 답변들의 총 난이도 값들
        double totalAmountBefore = this.difficulty * answers.size() ;

        // 새로운 난이도값 후보 ( => 소수점 둘째자리에서 반올림 해야 함 )
        double difficultyCaldidate = (totalAmountBefore + difficulty) / (answers.size() + 1);

        // 새로운 난이도 계산.
        this.difficulty = Math.round(difficultyCaldidate*10)/10.0;
    }


//    public void setFK(User user){ this.user = user; }

    public void editTitle(User user, String title){
        if(this.isOwnedBy(user)){
            this.title = title;
        } else {
            throw new IllegalArgumentException("수정 권한이 없는 유저입니다.");
        }
    }

    public void editContent(User user, String content){
        if(this.isOwnedBy(user)){
            this.content = content;
        } else {
            throw new IllegalArgumentException("수정 권한이 없는 유저입니다.");
        }
    }


    public boolean isOwnedBy (User user) { return (this.user.equals(user)); }

    public boolean contains (Answer answer) { return answers.contains(answer); }

    public void adopted(){ this.isClosed = true; }


    public void processSelectionResult(User questioner, Question question, Answer answer){

        if(question.isClosed()){
            throw new IllegalArgumentException("이미 채택완료된 질문입니다.");
        }

        if(question.isOwnedBy(questioner)){
            if(question.contains(answer)) {
                question.adopted();
                answer.selected();
                User answerer = answer.getUser();
                answerer.increasePoint(100);
                answerer.increaseRating(10);
                answerer.changeUserGrade(answerer.getRating());
            } else {
                throw new IllegalArgumentException("질문에 포함되지 않은 답변은 채택할 수 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("채택은 질문자만 할 수 있습니다.");
        }
    }




}
