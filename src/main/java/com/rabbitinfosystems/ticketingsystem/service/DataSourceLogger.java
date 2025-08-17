package com.rabbitinfosystems.ticketingsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSourceLogger implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Override
    public void run(String... args) {
        System.out.println("### SPRING DATASOURCE URL: " + datasourceUrl);
    }
}

