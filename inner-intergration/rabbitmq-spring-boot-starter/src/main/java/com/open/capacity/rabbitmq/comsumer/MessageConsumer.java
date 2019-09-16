package com.open.capacity.rabbitmq.comsumer;


import com.open.capacity.rabbitmq.common.DetailResponse;

/**
 * Created by littlersmall on 16/5/12.
 */
public interface MessageConsumer {
    DetailResponse consume();
}
