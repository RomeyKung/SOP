package com.example.lab6inclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Lab6inClassApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab6inClassApplication.class, args);
    }

}
