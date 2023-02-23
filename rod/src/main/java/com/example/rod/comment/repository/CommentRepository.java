package com.example.rod.comment.repository;

import com.example.rod.answer.entity.Answer;
import com.example.rod.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByAnswerId(Long answerId, Pageable pageable);

}
