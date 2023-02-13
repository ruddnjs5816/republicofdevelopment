package com.example.rod.rating.controller;

import com.example.rod.rating.service.SchedulingService;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@RestController
@EnableAsync // 비동기적 처리
@RequiredArgsConstructor
public class SchedulingController {

    private final SchedulingService schedulingService;

    @Scheduled(cron = "0 0 1 * * *")  // 새벽 1시마다 반복
    public void autoUpdateGrade(){
        schedulingService.updateAllMember();
    }

}
