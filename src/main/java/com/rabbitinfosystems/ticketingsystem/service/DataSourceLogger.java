package com.rabbitinfosystems.ticketingsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class DataSourceLogger implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSourceLogger.class);
    
    @Value("${spring.datasource.url:NOT_SET}")
    private String datasourceUrl;

    @Override
    public void run(String... args) {
        System.out.println("==== Datasource URL ====");
        System.out.println(datasourceUrl);
        System.out.println("========================");
    }
}

