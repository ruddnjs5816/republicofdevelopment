package com.example.rod.question.repository;

import com.example.rod.question.entity.QuestionHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionHashTagRepository extends JpaRepository<QuestionHashTag, Long> {


    Optional<QuestionHashTag> findByQuestionIdAndHashTagId(Long questionId, Long hashTagId);

    List<QuestionHashTag> findAllByQuestionId(Long questionId);

}
