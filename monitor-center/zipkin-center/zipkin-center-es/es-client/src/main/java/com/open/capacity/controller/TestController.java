package com.open.capacity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.open.capacity.service.TestService;

import brave.Span;
import brave.Tracing;

@RestController
public class TestController {

	/**
	 * 
	 * 手动埋点
	 * key
	 * resp_code
	 * resp_msg
	 */
	@Autowired
	Tracing tracing;
	@Autowired
	TestService testService ;

	@GetMapping("/hello")
	public String hello() {
		
		tracing.tracer().startScopedSpan("parentSpan");
		Span span = tracing.tracer().currentSpan();
		
		StackTraceElement[] stes = Thread.currentThread().getStackTrace();  
		//手动埋点
		span.tag("class", stes[1].getClassName() );
		span.tag("method", stes[1].getMethodName() );
		span.tag("resp_code", "0000");
		span.tag("resp_msg", "successs");
		testService.secondBiz();
		span.finish();

		return "hello";
	}
}
