/**
 * 
 */
package com.open.capacity.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/** 
* 类说明
* mq ttl消息 
*/
 
@Configuration
@SpringBootApplication
public class TestRabbitMqCenterApp {
	
	public static void main(String[] args) {
//		固定端口启动
		SpringApplication.run(TestRabbitMqCenterApp.class, args);
		
		 
		
	}

}
