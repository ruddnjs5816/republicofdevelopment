/*
package com.example.rod.answer.service;

import com.example.rod.answer.entity.AnswerEntity;
import com.example.rod.answer.entity.LikeAnswer;
import com.example.rod.answer.repository.answerRepository;
import com.example.rod.answer.repository.likeAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class likeService {

    private final answerRepository answerrepository;
    private final likeAnswerRepository likeAnswerRepository;

    // 좋아요 유무 체크
    @Transactional(readOnly = true)
    public boolean checkLike(Long answerId) {
        return likeAnswerRepository.existsById(answerId);
    }

    @Transactional
    public void likeAnswer(Long answerId) {
        AnswerEntity answer = answerrepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("찾는 답변이 없습니다."));

        // 좋아요 데이터가 없을 때
        if (!checkLike(answerId)) {
            likeAnswerRepository.save(new LikeAnswer(answer));
//            answer.setLikes(1);
            answer.setLikes(likeAnswerRepository.countLikeAnswerById(answer.getAnswerId()));
        // 좋아요 데이터가 있을 때
        } else {
            likeAnswerRepository.deleteById(answerId);
            answer.setLikes(likeAnswerRepository.countLikeAnswerById(answer.getAnswerId()));
//            answer.setLikes(0);
        }
    }
}
*/
