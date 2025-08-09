package com.rabbitinfosystems.ticketingsystem.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class AppConfig {

    @Bean
    @Profile("!local")
    public FirebaseAuth firebaseAuth() throws IOException {
        String credentialsJson = System.getenv("GOOGLE_CREDENTIALS");

        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8));
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        var apps = FirebaseApp.getApps();
        return apps.isEmpty()
                ? FirebaseAuth.getInstance(FirebaseApp.initializeApp(options))
                : FirebaseAuth.getInstance(apps.get(0));
    }

}
