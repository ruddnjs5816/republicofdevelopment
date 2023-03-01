package com.example.rod.user.repository;

import com.example.rod.user.entity.User;
import com.example.rod.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(Long userId);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(@NotNull String username);

    Integer countUsersByRole(UserRole role);
}
