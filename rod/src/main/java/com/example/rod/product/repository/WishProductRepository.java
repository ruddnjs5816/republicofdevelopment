package com.example.rod.product.repository;

import com.example.rod.product.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishProductRepository extends JpaRepository<Wish, Long> {
}
