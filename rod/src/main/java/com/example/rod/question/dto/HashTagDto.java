package com.example.rod.question.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class HashTagDto {

    private final List<String> hashTags = new ArrayList<>();

    public HashTagDto(String tagName) {
        hashTags.add(tagName);
    }
}
