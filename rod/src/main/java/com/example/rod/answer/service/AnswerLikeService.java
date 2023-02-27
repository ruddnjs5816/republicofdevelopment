package com.example.rod.answer.service;

import com.example.rod.answer.dto.LikeAnswerResponse;
import com.example.rod.security.details.UserDetailsImpl;

public interface AnswerLikeService {

    LikeAnswerResponse likeAnswer(Long answerId, UserDetailsImpl userDetails);

}
