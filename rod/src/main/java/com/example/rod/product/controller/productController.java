package com.example.rod.product.controller;

import com.example.rod.product.dto.productModifyRequestDto;
import com.example.rod.product.dto.productRequestDto;
import com.example.rod.product.dto.productResponseDto;
import com.example.rod.product.service.productService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class productController {

    private final productService productService;


    //상품 등록
    @PostMapping("/admin/shop/{productId}")
    public void createProduct(
            @RequestBody productRequestDto productRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        productService.createProduct(productRequestDto, userDetails.getUser());
    }

    //상품 수정
    @PutMapping("/admin/shop/{productId}")
    public void updateProduct(
            @PathVariable Long productId,
            @RequestBody productModifyRequestDto productModifyRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        productService.updateProduct(productId,productModifyRequestDto, userDetails.getUser());
    }
    //상품 삭제
    @DeleteMapping("/admin/shop/{productId}")
    public void deleteProduct(
            @PathVariable Long productId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        productService.deleteProduct(productId, userDetails.getUser());
    }

    //전체 상품 조회
    @GetMapping("/shop")
    public List<productResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    //개별 상품 조회
    @GetMapping("/shop/{productId}")
    public productResponseDto getProductByProductId(@PathVariable Long productId) {
        productResponseDto productResponseDto = productService.getProductByProductId(productId);

        return productResponseDto;
    }

}
