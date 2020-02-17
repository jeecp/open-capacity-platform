package com.open.capacity.message.reciver;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

//（非顺序消息默认重试16次，每次时间延后）
@Service
@RocketMQMessageListener(consumerGroup="consumer-group",topic="topic")
public class ReciverService implements RocketMQListener<String>{
	@Override
	public void onMessage(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
	}

	 
}