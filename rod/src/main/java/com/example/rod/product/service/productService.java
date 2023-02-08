/*
package com.example.rod.product.service;

import com.example.rod.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.rod.product.entity.productEntity;

@Service
@RequiredArgsConstructor
public class productService {

    private final ProductRepository productRepository;

    @Transactional
    void createProduct(ProductRequestDto productRequestDto, User user){

        ProductEntity product = productRepository.saveAndFlush(new Product(requestDto, user.getId()));

    }
}
*/
