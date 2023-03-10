
package com.example.rod.product.controller;

import com.example.rod.product.dto.ProductRequestDto;
import com.example.rod.product.dto.ProductResponseDto;
import com.example.rod.product.service.ProductService;
import com.example.rod.security.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //admin 상점에 기프티콘 정보 등록
    @PostMapping("/shop")
//    @PreAuthorize("hasRole('ADMIN')")
//    @Secured({"ROLE_ADMIN", "ROLE_SELLER"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(
            @RequestBody ProductRequestDto productRequestDto
    ) {
        try {
            productService.createProduct(productRequestDto);
        } catch (Exception e) {

        }
    }



    //전체 상품 조회
    @GetMapping("/shop")
    public List<ProductResponseDto> getAllProducts(@RequestParam("page") int page,
                                                   @RequestParam("size") int size) {
        return productService.getAllProducts(page-1, size);
    }

//    //개별 상품 조회
//    @GetMapping("/shop/{productId}")
//    public ProductResponseDto getProductByProductId(@PathVariable Long productId) {
//        ProductResponseDto productResponseDto = productService.getProductByProductId(productId);
//
//        return productResponseDto;
//    }

}

