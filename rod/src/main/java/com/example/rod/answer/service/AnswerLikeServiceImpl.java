package com.example.rod.answer.service;

import com.example.rod.answer.dto.LikeAnswerResponse;
import com.example.rod.answer.entity.Answer;
import com.example.rod.answer.entity.AnswerLike;
import com.example.rod.answer.repository.AnswerLikeRepository;
import com.example.rod.answer.repository.AnswerRepository;
import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerLikeServiceImpl implements AnswerLikeService {

    private final AnswerRepository answerRepository;
    private final AnswerLikeRepository answerLikeRepository;



    @Transactional
    @Override
    public LikeAnswerResponse likeAnswer(Long answerId, UserDetailsImpl userDetails){
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("찾는 답변이 없습니다.")
        );

        User user = userDetails.getUser();

        if(answer.isOwnedBy(user)){
            throw new IllegalArgumentException("자신의 답변에는 좋아요를 누를 수 없습니다.");
        } else {
            List<Long> likedUsersId = answerLikeRepository.selectUserIdsByAnswerId(answerId);

            Long userId = user.getUserId();

            boolean likedIdAlreadyExists = false;

            for(Long id : likedUsersId) {
                if(id.equals(userId)){  // 이미 좋아요를 누른 상태라면
                    likedIdAlreadyExists = true;
                    answerLikeRepository.deleteByUserAndAnswer(user, answer);    // 좋아요 삭제
                    answer.decreaseLikes();
                    break;
                }
            }
            if(!likedIdAlreadyExists){
                AnswerLike answerLike = AnswerLike.builder().
                        user(user).
                        answer(answer).build();
                answerLikeRepository.save(answerLike);
                answer.increaseLikes();
            }
        }
        return new LikeAnswerResponse(answerId);

    }
}
