package com.open.capacity.rabbitmq.service; 

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

/**
 * RabbitMQ
 * 发送ttl消息
 **/
@Service
public class RabbitSenderService {

    public static final Logger log= LoggerFactory.getLogger(RabbitSenderService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

 

    /**
     * 发送信息入死信队列，等待着一定时间失效超时打印ttl消息
     * @param orderCode
     */
    public void sendTTLExpireMsg(final String orderCode){
        try {
        			Map map = Maps.newHashMap() ;
        			map.put("hello", "11");
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.setExchange("dead.prod.exchange");
                    rabbitTemplate.setRoutingKey("dead.prod.routing.key");
                    rabbitTemplate.convertAndSend(map, new MessagePostProcessor() {
                        @Override
                        public Message postProcessMessage(Message message) throws AmqpException {
                            MessageProperties mp=message.getMessageProperties();
                            mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                            mp.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Map.class);

                            //动态设置TTL(为了测试方便，暂且设置60s)
                            mp.setExpiration("60000");
                            return message;
                        }
                    });
        }catch (Exception e){
            log.error("发送信息入死信队列，等待着一定时间-发生异常，消息为：{}",orderCode,e.fillInStackTrace());
        }
    }

}




























