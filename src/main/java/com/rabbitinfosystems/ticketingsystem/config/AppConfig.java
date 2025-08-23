package com.rabbitinfosystems.ticketingsystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Configuration
public class AppConfig {

    private final SecretsManagerClient secretsManagerClient;

    public AppConfig(SecretsManagerClient secretsManagerClient) {
        this.secretsManagerClient = secretsManagerClient;
    }

    @Bean
    @Profile("!local")
    public FirebaseApp firebaseApp() throws IOException {
        String secretJson = getSecret("ticketing/firebase/service-account");

        // Convert JSON string to stream
        ByteArrayInputStream serviceAccountStream =
                new ByteArrayInputStream(secretJson.getBytes(StandardCharsets.UTF_8));

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .build();

        FirebaseApp.getApps();
        return FirebaseApp.initializeApp(options);
    }

    @Bean
    @Profile("!local")
    public SecretsManagerClient secretsManagerClient() {
        return SecretsManagerClient.builder()
                .region(Region.AP_SOUTH_1)
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    public String getSecret(String secretName) {
        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder().secretId(secretName).build();
            return secretsManagerClient.getSecretValue(valueRequest).secretString();
        } catch(SecretsManagerException exception) {
            throw new RuntimeException("Failed to retrieve secret: " + secretName, exception);
        }
    }
}

