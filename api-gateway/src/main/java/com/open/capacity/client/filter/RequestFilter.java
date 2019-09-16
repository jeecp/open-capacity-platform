package com.open.capacity.client.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.open.capacity.log.util.LogUtil;

/**
 * zuul 前置过虑器
 * 设置traceid ，由于传递traceid跟踪
 * @author gitgeek
 *
 */
@Component
public class RequestFilter extends ZuulFilter {
	
	private static final int FILTER_ORDER = 1;
	private static final boolean SHOULD_FILTER = true;
	private static final String FILTER_TYPE = "pre";
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseFilter.class);

	/*** filter类型，前置过虑器，后置过虑器和路由过虑器 */
	@Override
	public String filterType() {
		return FILTER_TYPE;
	}

	/*** 返回一个整数值，表示filter执行顺序 */
	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

	/*** 返回一个boolean，表示该过虑器是否执行 */
	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}

	/*** 每次filter执行的代码 */
	@Override
	public Object run() {
		
		String traceId = MDC.get("X-B3-TraceId");
	    MDC.put(LogUtil.LOG_TRACE_ID, traceId);
		RequestContext requestContext = RequestContext.getCurrentContext();
		String URL = requestContext.getRequest().getRequestURL().toString();
		requestContext.addZuulRequestHeader(LogUtil.HTTP_HEADER_TRACE_ID, traceId);
		LOGGER.info("request url = " + URL + ", traceId = " + traceId);
		return null;
	}
}
