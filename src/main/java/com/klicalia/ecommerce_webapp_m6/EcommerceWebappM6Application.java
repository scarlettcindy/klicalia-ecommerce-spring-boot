package com.klicalia.ecommerce_webapp_m6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EcommerceWebappM6Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceWebappM6Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EcommerceWebappM6Application.class);
    }
}