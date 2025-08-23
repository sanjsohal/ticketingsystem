package com.rabbitinfosystems.ticketingsystem.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import com.rabbitinfosystems.ticketingsystem.service.AwsSecretsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Configuration
@Profile("!local")
public class FirebaseConfig {

    private final AwsSecretsService awsSecretsService;

    public FirebaseConfig(AwsSecretsService awsSecretsService) {
        this.awsSecretsService = awsSecretsService;
    }

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        String secretJson = awsSecretsService.getSecret("ticketing/firebase/service-account");

        // Convert JSON string to stream
        ByteArrayInputStream serviceAccountStream =
                new ByteArrayInputStream(secretJson.getBytes(StandardCharsets.UTF_8));

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .build();

        FirebaseApp.getApps();
        return FirebaseApp.initializeApp(options);
    }
}

