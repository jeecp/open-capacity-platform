package com.open.capacity.client.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.open.capacity.client.utils.TokenUtil;
import com.open.capacity.common.constant.TraceConstant;
import com.open.capacity.common.constant.UaaConstant;

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
		return -501;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		 
		String traceId = MDC.get(TraceConstant.LOG_B3_TRACEID);
		MDC.put(TraceConstant.LOG_TRACE_ID, traceId);
		
		String accessToken = TokenUtil.extractToken(exchange.getRequest());
		
		//构建head
		ServerHttpRequest traceHead = exchange.getRequest().mutate()
				 .header(TraceConstant.HTTP_HEADER_TRACE_ID, traceId )
				.header(UaaConstant.TOKEN_HEADER, accessToken ).build();
		//将现在的request 变成 change对象 

		log.info("request url = " + exchange.getRequest().getURI() + ", traceId = " + traceId);
		
		ServerWebExchange build = exchange.mutate().request(traceHead).build();
		
        return chain.filter(build);

		
	}

	 

}
