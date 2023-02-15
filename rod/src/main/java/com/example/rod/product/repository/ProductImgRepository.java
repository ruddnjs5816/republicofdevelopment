package com.example.rod.product.repository;

import com.example.rod.product.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
    List<ProductImg> findByProductIdOrderByIdAsc(Long productId);

    ProductImg findByProductIdAndRepImgYn(Long productId, String repImgYn);
}
