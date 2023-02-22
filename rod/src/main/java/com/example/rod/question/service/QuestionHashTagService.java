package com.example.rod.question.service;

import com.example.rod.question.dto.HashTagDto;
import com.example.rod.question.entity.HashTag;
import com.example.rod.question.entity.Question;
import com.example.rod.question.entity.QuestionHashTag;
import com.example.rod.question.repository.QuestionHashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class QuestionHashTagService {

    private final QuestionHashTagRepository questionHashTagRepository;

    private final HashTagService hashTagService;

    public void saveHashTags(Question question, String tagStrs){
        List<String> hashTags = Arrays.stream(tagStrs.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        hashTags.forEach(hashTagContent -> {
            saveHashTag(question, hashTagContent);
        });
    }

    public QuestionHashTag saveHashTag(Question question, String hashTagContent){

        HashTag hashTag = hashTagService.save(hashTagContent);

        Optional<QuestionHashTag> optQuestionHTag = questionHashTagRepository.findByQuestionQuestionIdAndHashTagId(question.getQuestionId(), hashTag.getId());

        if(optQuestionHTag.isPresent()){
            return optQuestionHTag.get();
        }

        QuestionHashTag questionHashTag = QuestionHashTag.builder()
                .question(question)
                .hashTag(hashTag)
                .build();

        questionHashTagRepository.save(questionHashTag);

        return questionHashTag;
    }

    public HashTagDto findTagsByQuestionId(Long questionId) {

        List<QuestionHashTag> questionHashTags = questionHashTagRepository.findAllByQuestionQuestionId(questionId);

        HashTagDto hashTagDtoList = new HashTagDto();

        questionHashTags.forEach(questionHashTag ->{
            String tagName = questionHashTag.getHashTag().getHashTagName();

            hashTagDtoList.getHashTags().add(tagName);
        });

        return hashTagDtoList;
    }




}
