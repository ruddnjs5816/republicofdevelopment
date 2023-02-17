//package com.example.rod.rating.controller;
//
//import com.example.rod.rating.dto.RatingDto;
//import com.example.rod.rating.service.RatingService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@Secured({"ROLE_ADMIN"})
//@RequiredArgsConstructor
//@RequestMapping("/api/admin")
//public class RatingController {
//
//    private final RatingService ratingService;
//
//    @PutMapping("/changed/{id}")
//    public void changeGrade(@PathVariable Long id, @RequestBody RatingDto ratingDto) {
//        ratingService.changeGrade(id, ratingDto);
//    }
//}
