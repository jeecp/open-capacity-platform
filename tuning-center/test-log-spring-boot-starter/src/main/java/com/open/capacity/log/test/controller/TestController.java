package com.open.capacity.log.test.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {
	 @Autowired
	    BeanFactory beanFactory;
	 
	
	@GetMapping("/test")
	public String opt(){
		
		log.info("ok");
		CompletableFuture.runAsync(() -> {
			
				try {
					
					Thread.sleep(10000);
					log.info("oook");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}, new TraceableExecutorService(beanFactory,  Executors.newFixedThreadPool(10),
		        // 'calculateTax' explicitly names the span - this param is optional
		        "calculateTax"));
		log.info("ok");
		return "ok" ;
	}
	
	 
}
