package com.open.capacity;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.open.capacity.client.vo.AuthIgnored;

 
@EnableCircuitBreaker
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(AuthIgnored.class)
public class ApiGateWayApp {
    public static void main(String[] args) {
        SpringApplication.run(ApiGateWayApp.class, args);
    }
}