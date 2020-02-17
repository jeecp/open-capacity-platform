package com.open.capacity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brave.Span;
import brave.Tracing;

@Service
public class TestService{

	/**
	 * 
	 * 手动埋点
	 * key
	 * resp_code
	 * resp_msg
	 */
	@Autowired
	Tracing tracing;
  
	public void secondBiz() {
		tracing.tracer().startScopedSpanWithParent("childSpan", tracing.tracer().currentSpan().context());
		//手动埋点
		Span chindSpan = tracing.tracer().currentSpan();
		StackTraceElement[] stes = Thread.currentThread().getStackTrace();  
		chindSpan.tag("class", stes[1].getClassName() );
		chindSpan.tag("method", stes[1].getMethodName() );
		chindSpan.tag("resp_code", "0000");
		chindSpan.tag("resp_msg", "successs");
		chindSpan.finish();
		System.out.println("end tracing,id:" + chindSpan.context().traceIdString());
	}

	 
}
