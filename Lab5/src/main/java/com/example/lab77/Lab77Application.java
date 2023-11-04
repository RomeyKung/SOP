package com.example.lab77;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Lab77Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab77Application.class, args);
    }

}
