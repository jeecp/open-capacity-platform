package com.open.capacity.client.handler;

import java.nio.charset.Charset;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 自定义鉴权失败时的处理逻辑resAuthenticationEntryPoint
 * 实现ServerAuthenticationEntryPoint并覆盖其commence方法即可:
 * 401未授权异常处理，转换为JSON
 *
 */
@Slf4j
public class ResAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
    	JSONObject message = new JSONObject();
		message.put("resp_code", HttpStatus.UNAUTHORIZED.value());
		message.put("resp_msg",  e.getMessage());
		
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setAccessControlAllowCredentials(true);
        response.getHeaders().setAccessControlAllowOrigin("*");
        response.setStatusCode( HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap(message.toJSONString().getBytes(Charset.defaultCharset()));
        return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
            DataBufferUtils.release(buffer);
        });
    }
}
