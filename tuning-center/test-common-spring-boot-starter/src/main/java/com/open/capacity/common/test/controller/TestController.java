package com.open.capacity.common.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.open.capacity.common.util.SysUserUtil;

@RestController
public class TestController {

	 
	
	@GetMapping("/test")
	public String opt(){
		
		System.out.println(SysUserUtil.getLoginAppUser());
		return "ok" ;
	}
	
	 
}
