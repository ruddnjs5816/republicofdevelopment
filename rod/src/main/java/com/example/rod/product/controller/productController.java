package com.example.rod.product.controller;

import com.example.rod.product.dto.productModifyRequestDto;
import com.example.rod.product.dto.productRequestDto;
import com.example.rod.product.dto.productResponseDto;
import com.example.rod.product.service.productService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class productController {

    private final productService productService;


    //상품 등록
    @PostMapping("/admin/shop")
    public void createProduct(
            @RequestBody productRequestDto productRequestDto
//            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        productService.createProduct(productRequestDto);
    }

    //상품 수정
    @PutMapping("/admin/shop/{productId}")
    public void updateProduct(
            @PathVariable Long productId,
            @RequestBody productModifyRequestDto productModifyRequestDto
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
    public List<productResponseDto> getAllProducts(@RequestParam("page") int page,
                                                   @RequestParam("size") int size,
                                                   @RequestParam("sortBy") String sortBy,
                                                   @RequestParam("isAsc") boolean isAsc) {
        return productService.getAllProducts(page-1, size, sortBy, isAsc);
    }

    //개별 상품 조회
    @GetMapping("/shop/{productId}")
    public productResponseDto getProductByProductId(@PathVariable Long productId) {
        productResponseDto productResponseDto = productService.getProductByProductId(productId);

        return productResponseDto;
    }

    //상품 주문
//    @PostMapping("/shop/{productId}")
    //상품 주문 내역 조회


}
