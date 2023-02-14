package com.example.rod.user.entity;


import com.example.rod.profile.entity.Profile;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String password;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int point;

    private String phoneNumber;

    private int rating;

    @Enumerated(value = EnumType.STRING)
    private UserGrade grade;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Question> questions = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Answer> answers = new ArrayList<>();

    public void changeRole(String role){
        this.grade = UserGrade.valueOf(role);
    }

    public void update(Long id, String username, String password, String phoneNumber) {
        this.userId = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
