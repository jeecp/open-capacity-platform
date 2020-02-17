package com.open.capacity.message.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.open.capacity.message.dao.BusinssDataMapper;
import com.open.capacity.message.dao.MqTransactionBusinssLogMapper;
import com.open.capacity.message.model.BusinessData;
import com.open.capacity.message.model.MqTransactionBusinessLog;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
/**
 * 测试消息入库
 * 业务库  --> 
 * 日志表  --> mq_trancation_business_log
 */
public class MessageService {
 
	@Autowired
	private MqTransactionBusinssLogMapper mqTransactionBusinssLogMapper ;
	
	@Autowired
	private BusinssDataMapper businssDataMapper ;
	
	@Autowired
	private RocketMQTemplate rocketMQTemplate;

	public void sendTransactionMessage( ){
		
		String id =UUID.randomUUID().toString();
		String transactionId = UUID.randomUUID().toString();
		
		Map message = Maps.newHashMap();
		message.put("id", id) ;
		message.put("username", "gitgeek") ;
		message.put("sex", "1") ;
		
		rocketMQTemplate.sendMessageInTransaction("txSaveProducerGroup", "trans-topic",
				MessageBuilder.withPayload(message)
				//事物消息
				.setHeaderIfAbsent(RocketMQHeaders.TRANSACTION_ID, transactionId)
				//业务数据id
				.setHeader("dataid", id)  
				//业务数据dmo
				.setHeader("message", JSON.toJSONString(message))
				.build() //真正的发现消息
				, JSON.toJSONString(message)) ;
		
	}
	 
     
	/**
	 * 本地核心业务逻辑
	 * @param id
	 * @param message
	 * @param transactionId
	 */
    @Transactional(rollbackFor = Exception.class)
    public void saveMessage(String id, Map message, String transactionId) {
    	//业务入库
    	businssDataMapper.save(BusinessData.builder().id(id).username(Optional.ofNullable(String.valueOf(message.get("username"))).orElseThrow(() -> new IllegalStateException("username is null!"))).sex(Optional.ofNullable(String.valueOf(message.get("sex"))).orElseThrow(() -> new IllegalStateException("sex is null!"))).build());
    	
    	//模拟异常，不会落地数据同时也不会发送消息
//    	System.out.println(1/0);	
    	
    	//mq日志消息入库
    	if(StringUtils.isNotBlank(transactionId)){
    		 mqTransactionBusinssLogMapper.save(MqTransactionBusinessLog.builder().id(id).transactionId(transactionId).description("测试").build()) ;
    	        
    	}
       
    }
}

