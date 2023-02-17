package com.example.rod.user.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class ApplyRequestDto {
    private final Long id;
    private final String status;
    private final Long user;

    public ApplyRequestDto(Long id, String status, Long user) {
        this.id = id;
        this.status = status;
        this.user = user;
    }
}
