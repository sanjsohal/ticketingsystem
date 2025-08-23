package com.rabbitinfosystems.ticketingsystem.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

@Service
public class AwsSecretsService {
    private final SecretsManagerClient secretsManagerClient;

    public AwsSecretsService(SecretsManagerClient secretsManagerClient) {
        this.secretsManagerClient = secretsManagerClient;
    }

    @Cacheable(value = "secretsCache", key="#secretName")
    public String getSecret(String secretName) {
        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder().secretId(secretName).build();
            return secretsManagerClient.getSecretValue(valueRequest).secretString();
        } catch(SecretsManagerException exception) {
            throw new RuntimeException("Failed to retrieve secret: " + secretName, exception);
        }
    }
}
