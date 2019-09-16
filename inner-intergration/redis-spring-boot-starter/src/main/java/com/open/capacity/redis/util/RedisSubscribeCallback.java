package com.open.capacity.redis.util;

 
public interface RedisSubscribeCallback {
    void callback(String msg);
}
