package com.rabbitinfosystems.ticketingsystem.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Slf4j
@Configuration
public class MinioConfig {

    @Bean
    public S3Presigner s3Presigner() {
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
