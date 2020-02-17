package com.open.capacity.common.feign;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.open.capacity.common.constant.TraceConstant;
import com.open.capacity.common.constant.UaaConstant;
import com.open.capacity.common.util.TokenUtil;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author 作者 owen E-mail: 624191343@qq.com
 * @version 创建时间：2017年11月12日 上午22:57:51
 * feign拦截器
 */
@Configuration
public class FeignInterceptorConfig {

	 
	@Bean
	public RequestInterceptor requestInterceptor() {
		RequestInterceptor requestInterceptor = new RequestInterceptor() {

			@Override
			public void apply(RequestTemplate template) {
				//传递token
				//使用feign client访问别的微服务时，将accessToken header 
				//config.anyRequest().permitAll() 非强制校验token
				if(StringUtils.isNotBlank(TokenUtil.getToken())){
					template.header(UaaConstant.TOKEN_HEADER, TokenUtil.getToken() );
				}
				
				

				//传递traceId
	            String traceId = StrUtil.isNotEmpty(MDC.get(TraceConstant.LOG_TRACE_ID))  ?  MDC.get(TraceConstant.LOG_TRACE_ID) :  MDC.get(TraceConstant.LOG_B3_TRACEID) ;
	            if (StrUtil.isNotEmpty(traceId)) {
	                template.header(TraceConstant.HTTP_HEADER_TRACE_ID, traceId);
	            }

				
			}
		};

		return requestInterceptor;
	}
}
