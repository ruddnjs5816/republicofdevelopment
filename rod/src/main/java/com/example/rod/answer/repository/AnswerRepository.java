package com.example.rod.answer.repository;

import com.example.rod.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findById(Long id);

}
