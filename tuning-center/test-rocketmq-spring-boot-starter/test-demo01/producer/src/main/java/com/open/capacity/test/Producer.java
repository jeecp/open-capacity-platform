package com.open.capacity.test;

import java.io.UnsupportedEncodingException;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class Producer {
	public static void main(String[] args) throws UnsupportedEncodingException {
		DefaultMQProducer producer = null ;
		try {

			producer = new DefaultMQProducer("my-producer-group");
			producer.setNamesrvAddr("192.168.235.170:9876");
			producer.start();
			 producer.setSendMsgTimeout(12000);
			for (int i = 0; i < 5; i++) {
				Message msg = new Message("TopicTest", "gitgeek", "KEY" + i,
						("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
				SendResult sendResult = producer.send(msg);

				System.out.println(sendResult);
			}

			
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			e.printStackTrace();
		}finally {
			producer.shutdown();
		}
	}
}