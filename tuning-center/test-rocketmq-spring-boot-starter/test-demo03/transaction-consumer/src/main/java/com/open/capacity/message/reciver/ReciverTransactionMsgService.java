package com.open.capacity.message.reciver;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup="tranc-consumer-group",topic="trans-topic")
public class ReciverTransactionMsgService implements RocketMQListener<String>{
	//消费端
	public void onMessage(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
	}

	 
}