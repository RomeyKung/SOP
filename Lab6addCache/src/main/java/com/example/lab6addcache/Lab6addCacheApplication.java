package com.example.lab6addcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Lab6addCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab6addCacheApplication.class, args);
    }

}
