package com.example.rod.product.repository;

import com.example.rod.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    Optional<Product> findById(Long id);

    Optional<Product> findByProductName(String productName);
}
