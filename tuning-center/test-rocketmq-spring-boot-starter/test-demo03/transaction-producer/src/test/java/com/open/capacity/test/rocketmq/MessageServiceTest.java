package com.open.capacity.test.rocketmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.open.capacity.MqCenter;
import com.open.capacity.message.service.MessageService;

/**
 * @SpringBootTest 会加载整个spring容器
 * MessageService 需要运行在spring容器环境中
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { MqCenter.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 配置启动类
public class MessageServiceTest {
 
	@Autowired
	private MessageService messageService ;
	
	
	@Test
	public void testsendTransactionMessage() {
		//模式发送分布式事物消息
		messageService.sendTransactionMessage();
	}
	
}

