package com.open.capacity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.open.capacity.service.SendService;

@RestController
public class SendController {

	@Autowired
	private SendService sendService ;
	
	@GetMapping("/send")
	public String send (){
		sendService.send();
		return "success ";
	}
}
