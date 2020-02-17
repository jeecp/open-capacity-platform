package com.open.capacity.test;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

public class Consumer {
 
     public static void main(String[] args) throws MQClientException {
         DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my-consumer-group");
         consumer.setNamesrvAddr("192.168.235.170:9876");
         consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
         consumer.subscribe("TopicTest", "gitgeek");
         consumer.registerMessageListener(new MessageListenerOrderly() {
             @Override
             public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                 context.setAutoCommit(false);
                 System.out.println(Thread.currentThread().getName()+"："+msgs);
                 return ConsumeOrderlyStatus.SUCCESS;
             }
         });
         consumer.start();
         System.out.printf("Consumer Started.%n");
     }
 }