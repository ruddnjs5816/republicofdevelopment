package com.example.rod.product.repository;

<<<<<<< HEAD
import com.example.rod.product.entity.productEntity;
=======
import com.example.rod.product.entity.Product;
>>>>>>> 1c95e1207484f7ea4693a8507f3b9111920e712f
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
