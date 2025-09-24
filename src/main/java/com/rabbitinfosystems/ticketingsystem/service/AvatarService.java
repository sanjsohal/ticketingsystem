package com.rabbitinfosystems.ticketingsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class AvatarService {

    private final S3Client s3Client;
    private final String bucketName = System.getenv("MINIO_BUCKET_NAME");

    public AvatarService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadAvatar(MultipartFile file, String firebaseUserId) throws IOException {
        String key = "users/" + firebaseUserId + "/" + file.getOriginalFilename();
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
        .contentType(file.getContentType())
                .build(), RequestBody.fromBytes(file.getBytes()));
        return key;
    }

    public String getAvatarUrl(String key) {
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toString();
    }
}
