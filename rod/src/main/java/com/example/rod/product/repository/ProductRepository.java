package com.example.rod.product.repository;

import com.example.rod.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

 public interface ProductRepository extends JpaRepository <Product, Long> {

    Optional<Product> findProductById(Long productId);
    Page<Product> findAll(Pageable pageable);

}
