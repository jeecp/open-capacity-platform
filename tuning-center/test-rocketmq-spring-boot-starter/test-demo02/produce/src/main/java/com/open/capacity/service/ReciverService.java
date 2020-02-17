package com.open.capacity.service;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup="consumer-group",topic="topic")
public class ReciverService implements RocketMQListener<String>{
	@Override
	public void onMessage(String msg) {
		// TODO Auto-generated method stub
		
		System.out.println(msg);
	}

	 
}