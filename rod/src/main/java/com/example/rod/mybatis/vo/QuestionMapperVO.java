package com.example.rod.mybatis.vo;

import com.example.rod.answer.entity.Answer;
import com.example.rod.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class QuestionMapperVO {
    private Long questionId;
    private String title;
    private String content;
    private Boolean isClosed;
    private Double difficulty;
    private User user;
    private List<Answer> answerList = new ArrayList<>();
}
