package com.open.capacity.client.filter;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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

import reactor.core.publisher.Mono;

/**
 * * 程序名 : AccessFilter 建立日期: 2018-09-09 作者 : someday 模块 : 网关 描述 : oauth校验 备注 :
 * version20180909001
 * <p>
 * 修改历史 序号 日期 修改人 修改原因
 */
@Component
public class AccessFilter implements GlobalFilter, Ordered {

	// url匹配器
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private AuthIgnored authIgnored;

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -500;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// TODO Auto-generated method stub

		String accessToken = extractToken(exchange.getRequest());

		// 默认
		boolean flag = false;

		for (String ignored : authIgnored.getIgnored()) {

			if (pathMatcher.match(ignored, exchange.getRequest().getPath().value())) {
				flag = true; // 白名单
			}

		}

		if (flag) {
			return chain.filter(exchange);
		} else {

			Map<String, Object> params = (Map<String, Object>) redisTemplate.opsForValue().get("token:" + accessToken);

			if (params != null) {
				return chain.filter(exchange);
			} else {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

				ServerHttpResponse response = exchange.getResponse();
				JSONObject message = new JSONObject();
				message.put("resp_code", -1);
				message.put("resp_msg", "未认证通过！");
				byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
				DataBuffer buffer = response.bufferFactory().wrap(bits);
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				// 指定编码，否则在浏览器中会中文乱码
				response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
				return response.writeWith(Mono.just(buffer));
			}

		}

	}

	protected String extractToken(ServerHttpRequest request) {
		List<String> strings = request.getHeaders().get("Authorization");
		String authToken = null;
		if (strings != null) {
			authToken = strings.get(0).substring("Bearer".length()).trim();
		}

		if (StringUtils.isBlank(authToken)) {
			strings = request.getQueryParams().get("access_token");
			if (strings != null) {
				authToken = strings.get(0);
			}
		}

		return authToken;
	}

}
