package com.example.webhookapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebhookappApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebhookappApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(" RUN METHOD EXECUTED");

        // DIRECT CALL (no Spring injection)
        new WebhookService().executeFlow();
    }
}
