package com.open.capacity.test.rocketmq;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.open.capacity.MqCenter;

/**
 * @SpringBootTest 会加载整个spring容器 RocketMQTemplateTest 需要运行在spring容器环境中
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
	public void testOldSend() {
		try {

			for (int i = 0; i < 5; i++) {
				Message msg = new Message("topic", "gitgeek", "KEY" + i,
						("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
				SendResult sendResult = rocketMQTemplate.getProducer().send(msg);

				System.out.println(sendResult);
			}

		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException
				| UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testNewSend() {
		rocketMQTemplate.convertAndSend("topic", "hello world ");
	}

	@Test
	public void testSendOneWay() {
		// 发送即发即失消息（不关心发送结果）
		rocketMQTemplate.sendOneWay("topic", MessageBuilder.withPayload("I'm one way message").build());
	}

	@Test
	public void testSendDelayed() {
		// 发送延迟消息
		// 1到18分别对应1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
		// 1 1s
		// 2 5s
		rocketMQTemplate.syncSend("topic", MessageBuilder.withPayload("I'm delay message").build(),
				rocketMQTemplate.getProducer().getSendMsgTimeout(), 5); // 5
																		// 代表1m

	}

	@Test
	public void testAsyncSend() {
		// 异步消息
		try {
			rocketMQTemplate.asyncSend("topic", MessageBuilder.withPayload("I'm async message").build(),
					new SendCallback() {
						@Override
						public void onSuccess(SendResult sendResult) {

							System.out.println(sendResult);
						}

						@Override
						public void onException(Throwable e) {
							// 补偿机制，根据业务需要，是否需要消息重试
							e.printStackTrace();
						}
					});

			TimeUnit.SECONDS.sleep(10);
			System.out.println("11111111111111111111");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testSelectSend() {

		try {
			Message msg = new Message("topic", "gitgeek", "KEY", ("queue 0").getBytes(RemotingHelper.DEFAULT_CHARSET));

			rocketMQTemplate.getProducer().send(msg, new MessageQueueSelector() {

				@Override
				// arg是由外部传入，本例0
				public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
					// TODO Auto-generated method stub
					int queueNum = Integer.valueOf(arg.toString());
					// 选择queue
					return mqs.get(queueNum);
				}
			}, 0); // 0传入匿名内部类MessageQueueSelector的Object arg

		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException
				| UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testSelectAsyncSend() {

		try {
			Message message = new Message("topic", "gitgeek", "KEY",
					("queue 0").getBytes(RemotingHelper.DEFAULT_CHARSET));

			rocketMQTemplate.getProducer().send(message, (mqs, msg, arg) -> {
				int queueNum = Integer.valueOf(arg.toString());
				// 选择queue
				return mqs.get(queueNum);
			}, 0, new SendCallback() {
				@Override
				public void onSuccess(SendResult sendResult) {

					System.out.println(sendResult);
				}

				@Override
				public void onException(Throwable e) {
					// 补偿机制，根据业务需要，是否需要消息重试
					e.printStackTrace();
				}
			});

			TimeUnit.SECONDS.sleep(10);
			System.out.println("11111111111111111111");

		} catch (MQClientException | RemotingException  | InterruptedException
				| UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}