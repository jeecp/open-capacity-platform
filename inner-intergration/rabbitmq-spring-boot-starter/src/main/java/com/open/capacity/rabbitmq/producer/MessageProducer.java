package com.open.capacity.rabbitmq.producer;

import com.open.capacity.rabbitmq.common.DetailResponse;

public interface MessageProducer {


    DetailResponse send(Object message);

}