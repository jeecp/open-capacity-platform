package com.open.capacity.client.filter;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.capacity.client.service.impl.SysClientServiceImpl;
import com.open.capacity.client.utils.RedisLimiterUtils;
import com.open.capacity.common.web.Result;
import com.open.capacity.redis.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Created by owen on 2018/12/10. 
 * 根据应用 url 限流 oauth_client_details if_limit 限流开关
 * limit_count 阈值
 */
@Slf4j
@Component
public class RateLimitFilter implements GlobalFilter, Ordered {
    // url匹配器
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Resource
    private RedisUtil redisUtil;

    @Resource
	private RedisTemplate<String, Object> redisTemplate ;
    
	@Autowired
	private RedisLimiterUtils redisLimiterUtils;
	@Autowired
	private ObjectMapper objectMapper;
	
	

	@Resource
	SysClientServiceImpl sysClientServiceImpl;

	 
    @Override
    public int getOrder() {
        return -500;
    }

    /**
     * 1. 判断token是否有效
     * 2. 如果token有对应clientId
     * 2.1 判断clientId是否有效
     * 2.2 判断请求的服务service是否有效
     * 2.3 判断clientId是否有权限访问service
     * 3. 判断 clientId+service 每日限流
     * @param exchange
     * @param accessToken
     * @return
     */

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    	String accessToken = extractToken(exchange.getRequest());
        if (!checkRateLimit(exchange, accessToken)) {
                log.error("TOO MANY REQUESTS!");
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                
                ServerHttpResponse response = exchange.getResponse();
                JSONObject message = new JSONObject();
                message.put("resp_code", -1);
                message.put("resp_msg", "TOO MANY REQUESTS!");
                byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                //指定编码，否则在浏览器中会中文乱码
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
                    DataBufferUtils.release(buffer);
                });

        }

 
        return chain.filter(exchange);
    }

    private String extractToken(ServerHttpRequest request) {
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

    
    private Boolean checkRateLimit(ServerWebExchange exchange, String accessToken) {
        try {
			String reqUrl = exchange.getRequest().getPath().value();

      // 1. 按accessToken查找对应的clientId
			Map<String, Object> params =  (Map<String, Object>) redisTemplate.opsForValue().get("token:" + accessToken) ;
//		
			
			if(params!=null){
				String clientId = String.valueOf(params.get("clientId")) ;
				Map client = sysClientServiceImpl.getClient(clientId);
				if(client!=null){
					String flag = String.valueOf(client.get("if_limit") ) ;
					
					if("1".equals(flag)){
						String accessLimitCount =   String.valueOf(client.get("limit_count") ) ;
						if (!accessLimitCount.isEmpty()) {
							Result result = redisLimiterUtils.rateLimitOfDay(clientId,  reqUrl ,
									Long.parseLong(accessLimitCount));
							if (-1 == result.getResp_code()) {
								log.error("token:" + accessToken + result.getResp_msg());
								// ((ResultMsg)
								// this.error_info.get()).setMsg("clientid:" +
								// client_id + ":token:" + accessToken + ":" +
								// result.getMsg());
								// ((ResultMsg) this.error_info.get()).setCode(401);
								return false;
							}
						}
					}
				}
				
				
			}
		} catch (Exception e) {
			StackTraceElement stackTraceElement= e.getStackTrace()[0];
			log.error("checkRateLimit:" + "---|Exception:" +stackTraceElement.getLineNumber()+"----"+ e.getMessage());
		}
        
       return true;
    }
     
}
