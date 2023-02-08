
package com.example.rod.answer.repository;

import com.example.rod.answer.entity.LikeAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeAnswerRepository extends JpaRepository<LikeAnswer, Long> {


    void deleteById(Long answerId);
    boolean existsById(Long answerId);

    int countLikeAnswerById(Long answerId);

}

