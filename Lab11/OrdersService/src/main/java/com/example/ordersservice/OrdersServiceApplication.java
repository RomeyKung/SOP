package com.example.ordersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class OrdersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersServiceApplication.class, args);
    }

}
