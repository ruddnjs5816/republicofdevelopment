package com.example.rod.answer.repository;

import com.example.rod.answer.entity.Answer;
import com.example.rod.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findById(Long id);

    Page<Answer> findByQuestion(Question question, Pageable pageable);

}
