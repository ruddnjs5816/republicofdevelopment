package com.example.rod.answer.service;

import com.example.rod.security.details.UserDetailsImpl;

public interface AnswerLikeService {

    void likeAnswer(Long answerId, UserDetailsImpl userDetails);

}
