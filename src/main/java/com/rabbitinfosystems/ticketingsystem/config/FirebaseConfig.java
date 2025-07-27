package com.rabbitinfosystems.ticketingsystem.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

//@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() throws Exception {
        String credentials = System.getenv("GOOGLE_CREDENTIALS");
        System.out.println("Credentials: " + credentials);
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ByteArrayInputStream(credentials.getBytes(StandardCharsets.UTF_8)));

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp.initializeApp(options);
    }
}
