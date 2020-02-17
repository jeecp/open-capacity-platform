package com.open.capacity.test.rocketmq;

import java.io.UnsupportedEncodingException;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
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
	
	@Test
	public void tesSend1() {
		try {

			 
			for (int i = 0; i < 5; i++) {
				Message msg = new Message("topic", "gitgeek", "KEY" + i,
						("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
				SendResult sendResult = rocketMQTemplate.getProducer().send(msg);

				System.out.println(sendResult);
			}

			
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} 

	}
	
	
}