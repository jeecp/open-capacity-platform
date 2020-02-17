package com.open.capacity.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.open.capacity.rabbitmq.service.RabbitSenderService;

@RestController
public class TestController {
	
	  @Autowired
	private RabbitSenderService rabbitSenderService ;
	
	@GetMapping("/hello")
	public String hello (){
		
		rabbitSenderService.sendTTLExpireMsg("hello");
		return "hello";
	}
	 
}
