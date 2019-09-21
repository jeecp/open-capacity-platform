package com.open.capacity.client.filter;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSONObject;
import com.open.capacity.client.vo.AuthIgnored;
import com.open.capacity.log.util.LogUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * * 程序名 : AccessFilter 建立日期: 2018-09-09 作者 : someday 模块 : 网关 描述 : oauth校验 备注 :
 * version20180909001
 * <p>
 * 修改历史 序号 日期 修改人 修改原因
 */
@Slf4j
@Component
public class RequestFilter implements GlobalFilter, Ordered {


	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -500;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		 
		String traceId = MDC.get("X-B3-TraceId");
		MDC.put(LogUtil.LOG_TRACE_ID, traceId);
		exchange.getRequest().mutate().header("traceid", traceId ).build();
		
		log.info("request url = " + exchange.getRequest().getPath().value() + ", traceId = " + traceId);
		
        return chain.filter(exchange);

		
	}

	 

}
