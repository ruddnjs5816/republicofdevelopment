package com.example.rod.question.repository;

import com.example.rod.question.entity.QuestionHashTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionHashTagRepository extends JpaRepository<QuestionHashTag, Long> {


    Optional<QuestionHashTag> findByQuestionQuestionIdAndHashTagId(Long questionId, Long hashTagId);

    List<QuestionHashTag> findAllByQuestionQuestionId(Long questionId);

    Page<QuestionHashTag> findByHashTag_HashTagName(String tagname, Pageable pageable);

    boolean existsByHashTagId(Long hashTagId);
}
