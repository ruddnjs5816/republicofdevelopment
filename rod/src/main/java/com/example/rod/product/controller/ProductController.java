
package com.example.rod.product.controller;

import com.example.rod.product.dto.ProductRequestDto;
import com.example.rod.product.dto.ProductModifyRequestDto;
import com.example.rod.product.dto.ProductResponseDto;
import com.example.rod.product.service.ProductService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    //상품 등록
    @PostMapping("/admin/shop")
    public void createProduct(
            @RequestBody ProductRequestDto productRequestDto
//            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        productService.createProduct(productRequestDto);
    }

    //상품 수정
    @PutMapping("/admin/shop/{productId}")
    public void updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductModifyRequestDto productModifyRequestDto
//            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        productService.updateProduct(productId,productModifyRequestDto);
    }
    //상품 삭제
    @DeleteMapping("/admin/shop/{productId}")
    public void deleteProduct(
            @PathVariable Long productId
            //@AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        productService.deleteProduct(productId);
    }

    //전체 상품 조회
    @GetMapping("/shop")
    public List<ProductResponseDto> getAllProducts(@RequestParam("page") int page,
                                                   @RequestParam("size") int size,
                                                   @RequestParam("sortBy") String sortBy,
                                                   @RequestParam("isAsc") boolean isAsc) {
        return productService.getAllProducts(page-1, size, sortBy, isAsc);
    }

    //개별 상품 조회
    @GetMapping("/shop/{productId}")
    public ProductResponseDto getProductByProductId(@PathVariable Long productId) {
        ProductResponseDto productResponseDto = productService.getProductByProductId(productId);

        return productResponseDto;
    }

}

