package com.example.rod.product.service;

import com.example.rod.product.entity.ProductImg;
import com.example.rod.product.repository.ProductImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityNotFoundException;


@RequiredArgsConstructor
@Transactional
@Service
public class ProductImgService {
    @Value("${productImgLocation}")
    private String productImgLocation;

    private final ProductImgRepository productImgRepository;
    private final FileService fileService;

    public void saveProductImg(ProductImg productImg, MultipartFile productImgFile) throws Exception {
        String oriImgName = productImgFile.getOriginalFilename();
        String productImgName = "";
        String imgUrl = "";

        //이미지 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            productImgName = fileService.uploadFile(productImgLocation, oriImgName, productImgFile.getBytes());
            imgUrl = "/images/item/" + productImgName;
        }

        //상품 이미지 정보 저장
        productImg.updateProductImg(oriImgName, productImgName, imgUrl);
        productImgRepository.save(productImg);
    }

    public void updateProductImg(Long productImgId, MultipartFile productImgFile) throws Exception {

        if (!productImgFile.isEmpty()) {
            ProductImg savedProductImg = productImgRepository.findById(productImgId).orElseThrow(EntityNotFoundException::new);

            //기존 이미지 삭제
            if (!StringUtils.isEmpty(savedProductImg.getProductImgName())) {
                fileService.deleteFile(productImgLocation + "/" + savedProductImg.getProductImgName());
            }

            String oriImgName = productImgFile.getOriginalFilename();
            //업데이트한 상품 이미지 파일 업로드
            String productImgName = fileService.uploadFile(productImgLocation, oriImgName, productImgFile.getBytes());
            String imgUrl = "/images/item/" + productImgName;
            //변경된 상품 이미지 정보 저장
            savedProductImg.updateProductImg(oriImgName, productImgName, imgUrl);
        }
    }
}
