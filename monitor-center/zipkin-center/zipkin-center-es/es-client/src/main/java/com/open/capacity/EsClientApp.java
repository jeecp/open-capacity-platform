package com.open.capacity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EsClientApp {


	public static void main(String[] args) {
		SpringApplication.run(EsClientApp.class, args);
	}
}
