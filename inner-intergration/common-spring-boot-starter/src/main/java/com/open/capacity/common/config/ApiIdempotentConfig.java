package com.open.capacity.common.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.open.capacity.common.interceptor.AccessLimitInterceptor;
import com.open.capacity.common.interceptor.ApiIdempotentInterceptor;
import com.open.capacity.redis.util.RedisUtil;

@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
public class ApiIdempotentConfig implements  WebMvcConfigurer {


    @Resource
    private RedisTemplate< String, Object> redisTemplate ;

    @Autowired
	private RedisUtil redisUtil;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new AccessLimitInterceptor(redisUtil)) ;
        registry.addInterceptor(new ApiIdempotentInterceptor(redisTemplate)).addPathPatterns("/**") ;
        
        
    }
}
