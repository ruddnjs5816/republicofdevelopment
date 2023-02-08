
package com.example.rod.answer.service;

import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.entity.LikeAnswer;
import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.answer.repository.LikeAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final AnswerRepository answerRepository;
    private final LikeAnswerRepository LikeAnswerRepository;

    // 좋아요 유무 체크
    @Transactional(readOnly = true)
    public boolean checkLike(Long answerId) {
        return LikeAnswerRepository.existsById(answerId);
    }

    @Transactional
    public void likeAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("찾는 답변이 없습니다."));

        // 좋아요 데이터가 없을 때
        if (!checkLike(answerId)) {
            LikeAnswerRepository.save(new LikeAnswer(answer));
//            answer.setLikes(1);
            answer.setLikes(LikeAnswerRepository.countLikeAnswerById(answer.getAnswerId()));
        // 좋아요 데이터가 있을 때
        } else {
            LikeAnswerRepository.deleteById(answerId);
            answer.setLikes(LikeAnswerRepository.countLikeAnswerById(answer.getAnswerId()));
//            answer.setLikes(0);
        }
    }
}
