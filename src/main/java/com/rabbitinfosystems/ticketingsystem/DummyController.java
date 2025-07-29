package com.rabbitinfosystems.ticketingsystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {
    @GetMapping("/api/public/dummy")
    public String dummyEndpoint() {
        return "Dummy endpoint is working!"+System.getenv("TEST_ENV");
    }
} 
