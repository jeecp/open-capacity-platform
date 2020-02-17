package com.open.capacity.client.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * * 程序名 : ErrorHandlerConfiguration
 * 建立日期: 2018-09-09
 * 作者 : someday
 * 模块 : 网关
 * 描述 : 覆盖默认的异常处理
 * 备注 : version20180909001
 * <p>
 * 修改历史
 * 序号 	       日期 		        修改人 		         修改原因
 */
@Slf4j
@Component
public class HystrixFallbackHandler implements HandlerFunction<ServerResponse> {

     
    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
    	
        serverRequest.attribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR)
            .ifPresent(originalUrls -> log.error("网关执行请求:{}失败,hystrix服务降级处理", originalUrls));

        Map resp = new HashMap();
        resp.put("resp_code", "9999");
        resp.put("resp_msg", "程序失败");
        return ServerResponse
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(resp));
    }
}
