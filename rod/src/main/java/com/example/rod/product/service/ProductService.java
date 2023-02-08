
package com.example.rod.product.service;

import com.example.rod.product.dto.productModifyRequestDto;
import com.example.rod.product.dto.productRequestDto;
import com.example.rod.product.dto.productResponseDto;
import com.example.rod.product.entity.productEntity;
import com.example.rod.product.repository.productRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class productService {

    private final productRepository productRepository;

    //상품 등록
    @Transactional
    public void createProduct(productRequestDto productRequestDto){
        productEntity product = productRepository.saveAndFlush(new productEntity(productRequestDto));
        productRepository.save(product);
    }


    //상품 수정
    @Transactional
    public void updateProduct(Long productId, productModifyRequestDto productModifyRequestDto) {
        productEntity product = productRepository.findById(productId).orElseThrow(  () -> new IllegalArgumentException("존재하지 않는 상품입니다.")
        );
        product.changeProductStatus(productModifyRequestDto);
        productRepository.save(product);
    }
    //상품 삭제
    @Transactional
    public void deleteProduct(Long productId) {
        productEntity product = productRepository.findById(productId).orElseThrow(  () -> new IllegalArgumentException("존재하지 않는 상품입니다.")
        );
        productRepository.delete(product);
    }


    //전체 상품 조회
    @Transactional(readOnly = true)
    public List<productResponseDto> getAllProducts(int page, int size, String sortBy, boolean isAsc) {
        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<productEntity> products = productRepository.findAll(pageable);
        List<productResponseDto> productResponseDtoList = new ArrayList<>();

        for (productEntity product : products) {
            productResponseDtoList.add(new productResponseDto(product));
        }
        return productResponseDtoList;
    }

    //개별 상품 조회
    @Transactional(readOnly = true)
    public productResponseDto getProductByProductId(Long productId) {
        productEntity product = findProduct(productId);
        return new productResponseDto(product);
    }
    public productEntity findProduct(Long productId) {
        Optional<productEntity> foundProduct = productRepository.findProductById(productId);
        if (foundProduct.isEmpty()) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다");
        }
        productEntity product = foundProduct.get();
        return product;
    }
}

