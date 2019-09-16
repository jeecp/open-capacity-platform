package com.open.capacity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EsClientApp {

	@GetMapping("/hello")
	public String hello(){
		return "hello" ;
	}
	
	@GetMapping("/world")
	public String world(){
		return "world" ;
	}
	@GetMapping("/test")
	public String test(){
		return "test" ;
	}

	
    public static void main(String[] args) {
        SpringApplication.run(EsClientApp.class, args);
    }
}
 