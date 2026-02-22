package com.prashant.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
<<<<<<< HEAD
=======

import org.springframework.scheduling.annotation.EnableAsync;
>>>>>>> fee7cd166534775e6ad71f788fdd7a2c74dd4f0d

@SpringBootApplication
public class PortfolioApplication {

    public static void main(String[] args) {
        // Load .env file if it exists
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        } catch (Exception e) {
            // .env not found, rely on system environment variables
        }
        SpringApplication.run(PortfolioApplication.class, args);
    }

}
