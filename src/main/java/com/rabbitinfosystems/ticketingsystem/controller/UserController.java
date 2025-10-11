package com.rabbitinfosystems.ticketingsystem.controller;

import com.google.firebase.auth.FirebaseToken;
import com.rabbitinfosystems.ticketingsystem.dto.RegistrationPayload;
import com.rabbitinfosystems.ticketingsystem.dto.UserInfoResponse;
import com.rabbitinfosystems.ticketingsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegistrationPayload registrationPayload) {
        userService.saveUser(registrationPayload);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getCurrentUser(@AuthenticationPrincipal FirebaseToken firebaseToken) {
        userService.updateEmailVerifiedFlag(firebaseToken.getUid(), firebaseToken.isEmailVerified());
        return ResponseEntity.ok(new UserInfoResponse(
                firebaseToken.getUid(),
                firebaseToken.getName(),
                firebaseToken.isEmailVerified()
        ));
    }

}
