package com.open.capacity;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.open.capacity.client.vo.AuthIgnored;
import com.open.capacity.common.config.TraceFilterConfig;

 
@EnableCircuitBreaker
@EnableDiscoveryClient
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = TraceFilterConfig.class)) 
@SpringBootApplication 
@EnableConfigurationProperties(AuthIgnored.class)
public class ApiGateWayApp {
    public static void main(String[] args) {
        SpringApplication.run(ApiGateWayApp.class, args);
    }
}