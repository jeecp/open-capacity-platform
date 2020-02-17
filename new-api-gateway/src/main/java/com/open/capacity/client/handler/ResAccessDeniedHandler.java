package com.open.capacity.client.handler;

import java.nio.charset.Charset;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 403拒绝访问异常处理，转换为JSON
 */
@Slf4j
public class ResAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
    	JSONObject message = new JSONObject();
		message.put("resp_code", HttpStatus.FORBIDDEN);
		message.put("resp_msg",  e.getMessage());
		
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setAccessControlAllowCredentials(true);
        response.getHeaders().setAccessControlAllowOrigin("*");
        response.setStatusCode( HttpStatus.FORBIDDEN);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap(message.toJSONString().getBytes(Charset.defaultCharset()));
        return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
            DataBufferUtils.release(buffer);
        });
    }
}
