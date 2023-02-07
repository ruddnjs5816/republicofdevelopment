package com.example.rod.comment.repository;

import com.example.rod.comment.entity.commentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface commentRepository extends JpaRepository<commentEntity, Long> {
}
