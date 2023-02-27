package com.example.rod.admin.repository;

import com.example.rod.admin.entity.Admin;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findAdminByAdminId(Long adminId);

    Optional<Admin> findByAdminName(String adminName);

    Boolean existsAdminByAdminName(String adminName);
}
