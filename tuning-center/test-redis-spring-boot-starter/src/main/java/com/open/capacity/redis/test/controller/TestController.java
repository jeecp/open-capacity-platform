package com.open.capacity.redis.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.open.capacity.redis.util.RedisUtil;

@RestController
public class TestController {

	@Autowired
	private RedisUtil redisUtil ;
	@Autowired
	private StringRedisTemplate stringRedisTemplate ;
	
	@GetMapping("/test")
	public String opt(){
		
		System.out.println(redisUtil.incr("hello", 1));

		stringRedisTemplate.opsForValue().set("2019-08-15", "hello") ;
		
		
		System.out.println(stringRedisTemplate.opsForValue().get("2019-08-15"));
		
		
		
		 
		
		return "ok" ;
	}
	
	@GetMapping("/send")
	public String optMSG(){
		
		redisUtil.publishMsg("hello1", "topic1", "world") ;
		return "ok" ;
	}
	
	@GetMapping("/say")
	public String getMsg(){
		
		System.out.println(redisUtil.getMsg("hello1", "topic1") );
		new Runnable() {
			
			@Override
			public void run() {
				while(true){
					// TODO Auto-generated method stub
					System.out.println(redisUtil.getMsg("hello1", "topic1") );
				}
			}
		}; 
		
		return "ok" ; 
	}
}
