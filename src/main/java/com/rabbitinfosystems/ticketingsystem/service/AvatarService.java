package com.rabbitinfosystems.ticketingsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;

@Service
public class AvatarService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final String bucketName = System.getenv("MINIO_BUCKET_NAME");

    public AvatarService(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Presigner = s3Presigner;
        this.s3Client = s3Client;
    }

    public void uploadAvatar(MultipartFile file, String firebaseUserId) throws IOException {
        String key = "users/avatars/" + firebaseUserId + "/" + file.getOriginalFilename();
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
        .contentType(file.getContentType())
                .build(), RequestBody.fromBytes(file.getBytes()));
    }

    public String getAvatarUrl(String firebaseUserId) {
        String prefix = "users/avatars";
        String path = "";
        if(!StringUtils.hasLength(firebaseUserId)) {
            path = prefix + "/default";
        } else {
            path = prefix + "/" + firebaseUserId + "/";
        }
        ListObjectsV2Request listObjectsReq = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(path)
                .maxKeys(1)
                .build();

        ListObjectsV2Response listResponse = s3Client.listObjectsV2(listObjectsReq);
        if(listResponse.contents().isEmpty()) {
            throw new RuntimeException("Avatar not found" + firebaseUserId);
        }
        String key = listResponse.contents().get(0).key();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(java.time.Duration.ofMinutes(60))
                .getObjectRequest(getObjectRequest)
                .build();
        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }
}
