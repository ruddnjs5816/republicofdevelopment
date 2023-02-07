package com.example.rod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RodApplication {

    public static void main(String[] args) {
        SpringApplication.run(RodApplication.class, args);
    }

}
