package com.example.rod.rating.service;

import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final UserRepository userRepository;

//    @Scheduled(cron = "0 0 1 * * *")  // 새벽 1시마다 반복
//    @Transactional
//    public void updateAllGrade(){
//        userRepository.save(updateAllMember());
//    }

//    @Scheduled(cron = "0 0 1 * * *")  // 새벽 1시마다 반복
    @Transactional
    public void updateAllMember() {
        List<User> all = userRepository.findAll();

        for (User u:all) {
            if(u.getRating()<=50){
                u.changeRole("ROLE_BRONZE");
            } else if (50<u.getRating()&&u.getRating()<=150) {
                u.changeRole("ROLE_SILVER");
            } else if (151<u.getRating()&&u.getRating()<=300) {
                u.changeRole("ROLE_GOLD");
            } else if (301<u.getRating()&&u.getRating()<=500) {
                u.changeRole("ROLE_PLATINUM");
            } else if (501<u.getRating()&&u.getRating()<=750) {
                u.changeRole("ROLE_DIAMOND");
            } else if (751<u.getRating()&&u.getRating()<=1050) {
                u.changeRole("ROLE_MASTER");
            } else {
                u.changeRole("ROLE_GRANDMASTER");
            }
        }
    }

    public void bronzeToSilver(){
    }

    public void silverToGold() {
    }

    public void goldToPlatinum(){
    }

    public void platinumToDiamond(){
    }

    public void diamondToMaster(){
    }

    public void masterToGrandMaster(){
    }

}
