
package com.example.rod.product.service;

import com.example.rod.product.dto.ProductRequestDto;
import com.example.rod.product.dto.ProductModifyRequestDto;
import com.example.rod.product.dto.ProductResponseDto;
import com.example.rod.product.entity.Product;
import com.example.rod.product.entity.ProductImg;
import com.example.rod.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImgService productImgService;

    //상품 등록
    @Transactional
    public void createProduct(ProductRequestDto productRequestDto, List<MultipartFile> productImgFileList) throws Exception {
        Product product = productRepository.saveAndFlush(new Product(productRequestDto));
        productRepository.save(product);

        //이미지 등록
        for (int i = 0, max = productImgFileList.size(); i < max; i++) {
            //파일이 있을 때만 저장
            if (productImgFileList.get(i).getSize() != 0) {
                ProductImg productImg = ProductImg.builder()
                        .product(product)
                        .repImgYn(i == 0 ? "Y" : "N") //첫 번째 이미지일 경우 대표 상품 이미지 여부 값 “Y”
                        .build();

                productImgService.saveProductImg(productImg, productImgFileList.get(i));
            }
        }
    }


    //상품 수정
    @Transactional
    public void updateProduct(Long productId, ProductModifyRequestDto productModifyRequestDto, List<MultipartFile> productImgFileList) throws Exception  {
        Product product = findProduct(productId);
        product.changeProductStatus(productModifyRequestDto);
        productRepository.save(product);

        List<Long> productImgIds = productModifyRequestDto.getProductImgIds();
        //이미지 등록
        for (int i = 0, max = productImgFileList.size(); i < max; i++) {
            productImgService.updateProductImg(productImgIds.get(i), productImgFileList.get(i));
        }
    }
    //상품 삭제
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = findProduct(productId);
        productRepository.delete(product);
    }


    //전체 상품 조회
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts(int page, int size, String sortBy, boolean isAsc) {
        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products = productRepository.findAll(pageable);
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            productResponseDtoList.add(new ProductResponseDto(product));
        }
        return productResponseDtoList;
    }

    //개별 상품 조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProductByProductId(Long productId) {
        Product product = findProduct(productId);
        return new ProductResponseDto(product);
    }
    public Product findProduct(Long productId) {
        Optional<Product> foundProduct = productRepository.findProductByProductId(productId);
        if (foundProduct.isEmpty()) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다");
        }
        Product product = foundProduct.get();
        return product;
    }
}

