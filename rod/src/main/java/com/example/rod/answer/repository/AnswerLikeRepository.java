package com.example.rod.answer.repository;

import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.entity.AnswerLike;
import com.example.rod.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerLikeRepository extends JpaRepository<AnswerLike, Long> {

    int countLikeAnswerById(Long id);

    @Query(value= "select user_id from answer_like where answer_id = :answerId", nativeQuery = true)
    List<Long> selectUserIdsByAnswerId(Long answerId);

    void deleteByUserAndAnswer(User user, Answer answer);


}
