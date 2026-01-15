package com.inditex.pricing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Inditex Pricing Service.
 * This service provides price queries for products based on brand, product ID, and application date.
 * 
 * @author Inditex Tech Team
 * @version 1.0.0
 */
@SpringBootApplication
public class PricingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingApplication.class, args);
    }
}
