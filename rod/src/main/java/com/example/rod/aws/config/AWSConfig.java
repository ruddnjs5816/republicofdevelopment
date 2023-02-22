package com.example.rod.aws.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.services.s3.AmazonS3;

@Configuration
public class AWSConfig {

    /**
     * Key는 중요정보이기 때문에 properties 파일에 저장한 뒤 가져와 사용하는 방법이 좋습니다.
     */
    private String iamAccessKey = "IAM 생성할 때 확인했던 AccessKey 입력"; // IAM Access Key
    private String iamSecretKey = "IAM 생성할 때 확인했던 SecretKey 입력"; // IAM Secret Key
    private String region = "ap-northeast-2"; // Bucket Region

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String regions;


    @Bean
    public AmazonS3 amazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(regions)
                .build();
    }
}