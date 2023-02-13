package com.example.rod.rating.dto;

import com.example.rod.user.entity.GradeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    private GradeType role;
}
