package com.rabbitinfosystems.ticketingsystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class FirebaseSecretService {

    private final AwsSecretsService secretsService;
    private static final String FIREBASE_SECRET_NAME = "ticketing/firebase/service-account";

    public FirebaseSecretService(AwsSecretsService secretsService) {
        this.secretsService = secretsService;
    }

    public FirebaseConfig getFirebaseConfig() {
        return secretsService.getSecretAsObject(FIREBASE_SECRET_NAME, FirebaseConfig.class);
    }

    public JsonNode getFirebaseConfigJson() {
        return secretsService.getJsonSecret(FIREBASE_SECRET_NAME);
    }

    // Firebase config DTO
    @Getter
    @Setter
    public static class FirebaseConfig {
        private String type;
        private String projectId;
        private String privateKeyId;
        private String privateKey;
        private String clientEmail;
        private String clientId;
        private String authUri;
        private String tokenUri;
        private String authProviderX509CertUrl;
        private String clientX509CertUrl;
    }
}
