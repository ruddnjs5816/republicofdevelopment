package com.example.rod.file.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    @Value("${upload.image.location}")
    private String location;

    @PostConstruct
    void postConstruct(){
        File dir = new File(location);
        if(!dir.exists()){
            dir.mkdir();
        }
    }

    public void upload(MultipartFile multipartFile, String filename){
        try{
            multipartFile.transferTo(new File(location+filename));
        } catch (IOException e) {
            throw new RuntimeException("파일을 업로드할 수 없습니다.");
        }
    }
}
