package com.open.capacity.message.listener;

import java.util.Map;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.alibaba.fastjson.JSON;
import com.open.capacity.message.dao.MqTransactionBusinssLogMapper;
import com.open.capacity.message.model.MqTransactionBusinessLog;
import com.open.capacity.message.service.MessageService;

import lombok.RequiredArgsConstructor;

@RocketMQTransactionListener(txProducerGroup = "txSaveProducerGroup")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SaveBusinessDataTransactionListener implements RocketMQLocalTransactionListener {
    private final MessageService messageService;
    private final MqTransactionBusinssLogMapper rocketmqTransactionLogMapper;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        MessageHeaders headers = msg.getHeaders();

        //发送半消息时的事物id
        String transactionId =String.valueOf(headers.get(RocketMQHeaders.TRANSACTION_ID));
        //发送半消息时的业务id
        String id =  String.valueOf(headers.get("dataid"));
        String dtoString = String.valueOf(headers.get("message"));   ;
        Map message = JSON.parseObject(dtoString, Map.class);
        

        try {
            this.messageService.saveMessage(id, message, transactionId);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        MessageHeaders headers = msg.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);

        // select * from xxx where transaction_id = xxx
        MqTransactionBusinessLog transactionLog = this.rocketmqTransactionLogMapper.findById(transactionId);
        if (transactionLog != null) {
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
