package com.rabbitinfosystems.ticketingsystem.controller;

import com.rabbitinfosystems.ticketingsystem.dto.RegistrationPayload;
import com.rabbitinfosystems.ticketingsystem.dto.VerifyEmailPayload;
import com.rabbitinfosystems.ticketingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegistrationPayload registrationPayload) {
        userService.saveUser(registrationPayload);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/verifyemail")
    public ResponseEntity<Void> verifyEmail(
            @RequestBody VerifyEmailPayload payload) {
        userService.verifyEmail(payload);
        return ResponseEntity.ok().build();
    }

}
