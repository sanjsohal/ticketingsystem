package com.rabbitinfosystems.ticketingsystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.rabbitinfosystems.ticketingsystem.service.AwsSecretsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Configuration
public class AppConfig {

    private final AwsSecretsService awsSecretsService;

    public AppConfig(AwsSecretsService awsSecretsService) {
        this.awsSecretsService = awsSecretsService;
    }

    @Bean
    @Profile("!local")
    public FirebaseApp firebaseApp() throws IOException {
        String secretJson = awsSecretsService.getSecret("ticketing/firebase/service-account");

        // Convert JSON string to stream
        ByteArrayInputStream serviceAccountStream =
                new ByteArrayInputStream(secretJson.getBytes(StandardCharsets.UTF_8));

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
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
}

