package com.open.capacity.test.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.open.capacity.MqCenter;

/**
 * @SpringBootTest 会加载整个spring容器
 * RocketMQTemplateTest 需要运行在spring容器环境中
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { MqCenter.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 配置启动类
public class RocketMQTemplateTest {

	@Autowired
	private RocketMQTemplate rocketMQTemplate;

	
	@Test
	public void tesNamesrvAddr() {
		String namesrvAddr = rocketMQTemplate.getProducer().getNamesrvAddr();
		Assert.assertEquals("192.168.235.170:9876", namesrvAddr);
	}
	
	@Test
	public void tesSend() {
		rocketMQTemplate.convertAndSend("topic", "hello world ");
	}

}