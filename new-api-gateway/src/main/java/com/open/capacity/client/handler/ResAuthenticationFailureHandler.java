package com.open.capacity.client.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;
/**
 * 认证失败处理器resAuthenticationFailureHandler,
 * 实现ServerAuthenticationFailureHandler并覆盖其onAuthenticationFailure自定义认证失败的处理逻辑，本例中仅返回认证失败的响应信息
 *
 */
public class ResAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException e) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        //设置headers
        response.getHeaders().setAccessControlAllowCredentials(true);
        response.getHeaders().setAccessControlAllowOrigin("*");
        response.setStatusCode( HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        //设置body
        Map message = new HashMap();
		message.put("resp_code", HttpStatus.UNAUTHORIZED.value());
		message.put("resp_msg",  e.getMessage());
		
        byte[]   dataBytes={};
        try {
            ObjectMapper mapper = new ObjectMapper();
            dataBytes=mapper.writeValueAsBytes(message);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
        return response.writeWith(Mono.just(bodyDataBuffer)).doOnError((error) -> {
            DataBufferUtils.release(bodyDataBuffer);
        });
    }
}