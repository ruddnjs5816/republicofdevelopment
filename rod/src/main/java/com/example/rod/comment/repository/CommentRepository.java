package com.example.rod.comment.repository;

import com.example.rod.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface commentRepository extends JpaRepository<Comment, Long> {
}
