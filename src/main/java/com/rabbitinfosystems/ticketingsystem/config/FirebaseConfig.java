package com.rabbitinfosystems.ticketingsystem.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Configuration
@Profile("!local")
public class FirebaseConfig {


    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        System.out.println("=== FIREBASE CONFIG METHOD CALLED ===");
        System.out.println("This is a hardcoded test message");

        String secretJson = System.getenv("GOOGLE_CREDENTIALS");
        System.out.println(secretJson.substring(0, 200));


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

