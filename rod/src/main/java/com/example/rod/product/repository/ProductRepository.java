package com.example.rod.product.repository;

import com.example.rod.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {

    Optional<Product> findProductByProductId(Long productId);
    Page<Product> findAll(Pageable pageable);

}
