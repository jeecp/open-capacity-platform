package com.open.capacity.rabbitmq.producer;

import com.open.capacity.rabbitmq.common.DetailResponse;

public interface MessageSender {


    DetailResponse send(Object message);

    DetailResponse send(MessageWithTime messageWithTime);
}