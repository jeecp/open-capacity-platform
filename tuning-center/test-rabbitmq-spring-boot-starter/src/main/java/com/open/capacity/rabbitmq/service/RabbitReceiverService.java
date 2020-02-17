package com.open.capacity.rabbitmq.service; 

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * RabbitMQ接收消息服务
 **/
@Slf4j
@Service
public class RabbitReceiverService {

    
    /**
     * ttl消息
     * @param info
     */
    @RabbitListener(queues = {"dead.real.queue"},containerFactory = "multiListenerContainer")
    public void consumeExpirMsg(Map info){
        try {
            log.info("ttl消息",info);
            ObjectMapper mapper = new ObjectMapper();
            log.info(mapper.writeValueAsString(info));
        }catch (Exception e){
            log.error("ttl消息-监听者-发生异常：",e.fillInStackTrace());
        }
    }
}












