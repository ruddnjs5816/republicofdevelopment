
package com.example.rod.product.controller;

import com.example.rod.product.dto.ProductRequestDto;
import com.example.rod.product.dto.ProductModifyRequestDto;
import com.example.rod.product.dto.ProductResponseDto;
import com.example.rod.product.service.ProductService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;



import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;




    //전체 상품 조회
    @GetMapping("/shop")
    public List<ProductResponseDto> getAllProducts(@RequestParam("page") int page,
                                                   @RequestParam("size") int size,
                                                   @RequestParam("sortBy") String sortBy,
                                                   @RequestParam("isAsc") boolean isAsc) {
        return productService.getAllProducts(page-1, size, sortBy, isAsc);
    }

//    //개별 상품 조회
//    @GetMapping("/shop/{productId}")
//    public ProductResponseDto getProductByProductId(@PathVariable Long productId) {
//        ProductResponseDto productResponseDto = productService.getProductByProductId(productId);
//
//        return productResponseDto;
//    }

}

