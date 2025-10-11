package com.rabbitinfosystems.ticketingsystem.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Slf4j
@Configuration
public class MinioConfig {

    @Bean
    public S3Client s3Client() {
        log.debug("s3Client MINIO_ENDPOINT: {}", System.getenv("MINIO_ENDPOINT"));
        log.debug("s3Client MINIO_STORAGE_ENDPOINT: {}", System.getenv("MINIO_STORAGE_ENDPOINT"));
        log.debug("s3Client MINIO_ROOT_USER: {}", System.getenv("MINIO_ROOT_USER"));
        log.debug("s3Client MINIO_ROOT_PASSWORD: {}", System.getenv("MINIO_ROOT_PASSWORD"));
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

    @Bean
    public S3Presigner s3Presigner() {
        log.debug("s3Presigner MINIO_ENDPOINT: {}", System.getenv("MINIO_ENDPOINT"));
        log.debug("s3Presigner MINIO_STORAGE_ENDPOINT: {}", System.getenv("MINIO_STORAGE_ENDPOINT"));
        log.debug("s3Presigner MINIO_ROOT_USER: {}", System.getenv("MINIO_ROOT_USER"));
        log.debug("s3Presigner MINIO_ROOT_PASSWORD: {}", System.getenv("MINIO_ROOT_PASSWORD"));
        S3Configuration s3Configuration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build();
        return S3Presigner.builder()
                .endpointOverride(URI.create(System.getenv("MINIO_STORAGE_ENDPOINT")))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                System.getenv("MINIO_ROOT_USER"),
                                System.getenv("MINIO_ROOT_PASSWORD"))
                ))
                .region(Region.US_EAST_1)
                .serviceConfiguration(s3Configuration)
                .build();
    }
}
