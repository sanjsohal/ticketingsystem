package com.rabbitinfosystems.ticketingsystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

@Service
public class AwsSecretsService {
    private final SecretsManagerClient secretsManagerClient;
    private final ObjectMapper objectMapper;

    public AwsSecretsService(SecretsManagerClient secretsManagerClient, ObjectMapper objectMapper) {
        this.secretsManagerClient = secretsManagerClient;
        this.objectMapper = objectMapper;
    }

    @Cacheable(value = "secretsCache", key="#secretName")
    public String getSecretString(String secretName) {
        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder().secretId(secretName).build();
            return secretsManagerClient.getSecretValue(valueRequest).secretString();
        } catch(SecretsManagerException exception) {
            throw new RuntimeException("Failed to retrieve secret: " + secretName, exception);
        }
    }

    @Cacheable(value = "jsonSecretsCache", key = "#secretName")
    public JsonNode getJsonSecret(String secretName) {
        try {
            String secretValue = getSecretString(secretName);
            return objectMapper.readTree(secretValue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON secret: " + secretName, e);
        }
    }

    public <T> T getSecretAsObject(String secretName, Class<T> valueType) {
        try {
            String secretValue = getSecretString(secretName);
            return objectMapper.readValue(secretValue, valueType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse secret as object: " + secretName, e);
        }
    }
}
