package com.supasoft.partnerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main Application class for Partner Service
 * Handles Customer and Supplier management
 */
@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
public class PartnerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartnerServiceApplication.class, args);
    }
}

