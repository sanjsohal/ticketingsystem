package com.rabbitinfosystems.ticketingsystem.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Slf4j
@Configuration
public class MinioConfig {

    @Bean
    public S3Client s3Client() {
        log.info("MINIO_ROOT_USER: {}", System.getenv("MINIO_ROOT_USER"));
        log.info("MINIO_ROOT_PASSWORD: {}", System.getenv("MINIO_ROOT_PASSWORD"));
        return S3Client.builder()
                .endpointOverride(URI.create(System.getenv("MINIO_ENDPOINT")))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                System.getenv("MINIO_ROOT_USER"),
                                System.getenv("MINIO_ROOT_PASSWORD"))
                        ))
                .build();
    }
}
