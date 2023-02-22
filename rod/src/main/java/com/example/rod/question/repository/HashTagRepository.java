package com.example.rod.question.repository;

import com.example.rod.question.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    Optional<HashTag> findByhashTagName(String hashTagName);

}
