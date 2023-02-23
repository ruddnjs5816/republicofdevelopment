package com.example.rod.question.service;

import com.example.rod.question.entity.HashTag;
import com.example.rod.question.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public HashTag save(String hashTagContent){

        Optional<HashTag> optHashTag = hashTagRepository.findByhashTagName(hashTagContent);

        if( optHashTag.isPresent() ){   // 이미 존재하는 키워드이면,
            return optHashTag.get();
        }

        HashTag hashTag = HashTag.builder()
                .hashTagName(hashTagContent)
                .build();

        hashTagRepository.save(hashTag);
        return hashTag;
    }

    public void deleteHashTagsById(List<Long> noMatchingTagIds) {
        noMatchingTagIds.forEach(noMatchingTagId->{
           hashTagRepository.deleteById(noMatchingTagId);
        });
    }
}
