package com.example.rod.answer.repository;

import com.example.rod.answer.entity.Answer;
import com.example.rod.question.entity.Question;
import com.example.rod.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findById(Long id);

    Page<Answer> findByQuestion(Question question, Pageable pageable);

    Page<Answer> findByUser(User user, Pageable pageable);

    List<Answer> findByQuestionAndIsSelected(Question question, boolean isSelected);

    Page<Answer> findByQuestionAndIsSelected(Question question, boolean isSelected, Pageable pageable);

    Long countByQuestionQuestionId(Long questionId);

}
