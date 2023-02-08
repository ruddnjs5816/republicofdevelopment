/*
package com.example.rod.product.service;

import com.example.rod.product.dto.ProductRequestDto;
import com.example.rod.product.entity.Product;
import com.example.rod.product.repository.ProductRepository;
import com.example.rod.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    void createProduct(ProductRequestDto productRequestDto, User user){

        Product product = productRepository.saveAndFlush(new Product(productRequestDto, user.getUserId()));

    }
}
*/
