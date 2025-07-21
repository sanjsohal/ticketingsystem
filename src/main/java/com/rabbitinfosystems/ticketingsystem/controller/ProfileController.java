package com.rabbitinfosystems.ticketingsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("/api/profile")
    public ResponseEntity<String> getProfile(Authentication authentication) {
        String firebaseUid = (String) authentication.getPrincipal();
        return ResponseEntity.ok("Firebase UID: " + firebaseUid);
    }

}
