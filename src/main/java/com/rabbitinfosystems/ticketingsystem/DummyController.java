package com.rabbitinfosystems.ticketingsystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {
    @GetMapping("/dummy")
    public String dummyEndpoint() {
        return "Dummy endpoint is working!";
    }
} 