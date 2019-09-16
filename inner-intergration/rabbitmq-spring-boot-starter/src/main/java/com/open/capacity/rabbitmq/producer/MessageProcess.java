package com.open.capacity.rabbitmq.producer;



import com.open.capacity.rabbitmq.common.DetailResponse;


public interface MessageProcess<T> {
    DetailResponse process(T message);
}
