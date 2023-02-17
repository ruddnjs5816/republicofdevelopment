package com.example.rod.product.repository;

import com.example.rod.product.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {

//    List<ProductImg> findByProductOrderByIdAsc(Long productId);
    //    List<ProductImg> findProductImgByOrderByProductIdAsc(Long productId);


//    ProductImg findByProductIdAndRepImgYn(Long productId, String repImgYn);
}
