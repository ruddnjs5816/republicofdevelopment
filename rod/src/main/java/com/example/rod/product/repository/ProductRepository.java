package com.example.rod.product.repository;

import com.example.rod.product.entity.productEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface productRepository extends JpaRepository<productEntity, Long> {

    Optional<productEntity> findProductById(Long productId);
    Page<productEntity> findAll(Pageable pageable);
}
