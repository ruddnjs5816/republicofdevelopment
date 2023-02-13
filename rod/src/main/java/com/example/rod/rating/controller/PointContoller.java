package com.example.rod.rating.controller;

import com.example.rod.rating.dto.PointRequestDto;
import com.example.rod.rating.service.PointService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
//@Secured({"ROLE_ADMIN"})
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class PointContoller {

    private final PointService pointService;
    @PostMapping("/point/{id}")
    public void plusPoint(@PathVariable Long id, @RequestBody PointRequestDto pointDto){
        pointService.plusPoint(id,pointDto);
    }

    @PostMapping("/")
    public void minusPoint(@PathVariable Long id, @RequestBody PointRequestDto pointDto){
        pointService.minusPoint(id,pointDto);
    }
}
