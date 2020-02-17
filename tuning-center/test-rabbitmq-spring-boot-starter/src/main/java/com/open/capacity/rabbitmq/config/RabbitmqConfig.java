package com.open.capacity.rabbitmq.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.google.common.collect.Maps;

/**
 * 通用化 Rabbitmq 配置
 */
@Configuration
public class RabbitmqConfig {

    private final static Logger log = LoggerFactory.getLogger(RabbitmqConfig.class);

    @Autowired
    private Environment env;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 单一消费者
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        return factory;
    }

    /**
     * 多个消费者
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //确认消费模式-NONE
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.simple.concurrency",int.class));
        factory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.simple.max-concurrency",int.class));
        factory.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.simple.prefetch",int.class));
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
			}
			 
            
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.warn("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }

 


    

    //基本交换机
    @Bean
    public TopicExchange deadProdExchange(){
    	// 基本交换机名称：dead.prod.exchange
        return new TopicExchange("dead.prod.exchange",true,false);
    }

    //创建基本交换机+基本路由 -> 死信队列 的绑定
    @Bean
    public Binding deadProdBinding(){
    	
    	// deadProdExchange() 基本交换机
    	// dead.prod.routing.key 基本路由
    	// deadQueue() 死信队列
        return BindingBuilder.bind(deadQueue()).to(deadProdExchange()).with("dead.prod.routing.key");
    }

    //构建死信队列消息模型
    @Bean
    public Queue deadQueue(){
        Map<String, Object> argsMap= Maps.newHashMap();
        //死信交换机
        //死信路由
        argsMap.put("x-dead-letter-exchange","dead.exchange");
        argsMap.put("x-dead-letter-routing-key","dead.routing.key");
        //死信队列名称：dead.queue
        return new Queue("dead.queue",true,false,false,argsMap);
    }
    
    //死信交换机
    @Bean
    public TopicExchange deadExchange(){
        return new TopicExchange("dead.exchange",true,false);
    }
    
    //真正的队列
    @Bean
    public Queue realQueue(){
        return new Queue("dead.real.queue",true);
    }

    

    //死信交换机+死信路由->真正队列 的绑定
    @Bean
    public Binding deadBinding(){
    	
    	//deadExchange     死信交换机
    	//dead.routing.key 死信路由
    	//realQueue        真正队列
        return BindingBuilder.bind(realQueue()).to(deadExchange()).with("dead.routing.key");
    }
}






























































































