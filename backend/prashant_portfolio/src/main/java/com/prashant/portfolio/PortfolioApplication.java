package com.prashant.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main entry point for the Portfolio Backend Application.
 * 
 * ANNOTATIONS:
 * - @SpringBootApplication: Autoconfiguration, Component Scan, and Configuration.
 * - @EnableAsync: Enables Spring's asynchronous method execution capability, 
 *   critical for non-blocking email services.
 */
@SpringBootApplication
@EnableAsync
public class PortfolioApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }
}
