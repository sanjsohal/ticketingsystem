package com.rabbitinfosystems.ticketingsystem.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegistrationPayload registrationPayload) {
        userService.saveUser(registrationPayload);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
