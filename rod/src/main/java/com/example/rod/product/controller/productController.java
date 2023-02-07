package com.example.rod.product.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class productController {

    private final ProductService productService;


    //상품 등록
    @PostMapping("/admin/shop/{productId}")
    public void createProduct(
            @RequestBody ProductRequestDto productRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        productService.createProduct(productRequestDto, userDetails.getUser());
    }

}
